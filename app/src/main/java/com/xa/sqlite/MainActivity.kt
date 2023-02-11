package com.xa.sqlite

import android.app.AlertDialog.Builder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var edName: EditText
    private lateinit var edEmail: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnView: Button
    private lateinit var btnUpdate: Button
    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: StudentAdapter? = null
    private var std: StudentModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initRecyclerView()

        sqLiteHelper = SQLiteHelper(this)
        btnAdd.setOnClickListener { addStudent() }
        btnView.setOnClickListener { getStudents() }
        btnUpdate.setOnClickListener { updateStudent() }

        adapter?.setOnClickItem {
            Toast.makeText(this, "${it.name}", Toast.LENGTH_SHORT).show()
            edName.setText(it.name)
            edEmail.setText(it.email)
            std = it
        }
        adapter?.setOnClickDeliteItem { deliteStudent(it.id) }


    }

    private fun getStudents() {
        val stdList = sqLiteHelper.getAllStudent()
        Log.e("pppp", "${stdList.size} ")
        adapter?.addItem(stdList)
    }

    private fun deliteStudent(id:Int){
        if (id == null) return

        val builder =AlertDialog.Builder(this)
        builder.setMessage("are you sure delete this")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes"){ dialog,_ ->
            sqLiteHelper.deleteStudent(id)
            getStudents()
            dialog.dismiss()
        }
        builder.setNegativeButton("No"){dialog,_ ->
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }

    private fun addStudent() {
        val name = edName.text.toString()
        val email = edEmail.text.toString()

        if (name.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Please enter a required field", Toast.LENGTH_SHORT).show()
        } else {
            val std = StudentModel(name = name, email = email)
            val status = sqLiteHelper.insertStudent(std)

            if (status > -1) {
                Toast.makeText(this, "student added...", Toast.LENGTH_SHORT).show()
                clearEditText()

            } else {
                Toast.makeText(this, "add faild", Toast.LENGTH_SHORT).show()
            }
        }


    }

    //check changes
    private fun updateStudent() {
        val name = edName.text.toString()
        val email = edEmail.text.toString()

        if (name == std?.name && email == std?.email) {
            Toast.makeText(this, "record not changed..", Toast.LENGTH_SHORT).show()
            return
        }
        if (std == null) return

        val std = StudentModel(id = std!!.id, name = name, email = email)
        val status = sqLiteHelper.updateStudent(std)
        if (status > -1){
            clearEditText()
            getStudents()
        }else{
            Toast.makeText(this, "Update failed 😎 ", Toast.LENGTH_SHORT).show()
        }


    }

    private fun clearEditText() {
        edName.setText("")
        edEmail.setText("")
        edName.requestFocus()

    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = StudentAdapter()
        recyclerView.adapter = adapter
    }

    private fun initView() {
        edName = findViewById(R.id.editname)
        edEmail = findViewById(R.id.editemail)
        btnAdd = findViewById(R.id.btn_add)
        btnView = findViewById(R.id.btn_view)
        recyclerView = findViewById(R.id.recycler_view)
        btnUpdate = findViewById(R.id.btn_update)

    }


}






























