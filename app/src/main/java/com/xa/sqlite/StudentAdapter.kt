package com.xa.sqlite

import android.annotation.SuppressLint
import android.app.Notification
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {
    private var stdList: ArrayList<StudentModel> = ArrayList()
    private var onClickItem: ((StudentModel) -> Unit)? = null
    private var onClickDeleteItem: ((StudentModel) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun addItem(item: ArrayList<StudentModel>) {
        this.stdList = item
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback: (StudentModel) -> Unit) {
        this.onClickItem = callback
    }

    fun setOnClickDeliteItem(callback: (StudentModel) -> Unit){
        this.onClickDeleteItem = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.card_item_std, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val std = stdList[position]
        holder.bindview(std)
        holder.itemView.setOnClickListener { onClickItem?.invoke(std) }
        holder.btn_delete.setOnClickListener { onClickDeleteItem?.invoke(std) }
    }

    override fun getItemCount(): Int {
        return stdList.size
    }

    //view holder
    class StudentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var id = view.findViewById<TextView>(R.id.tvid)
        var name = view.findViewById<TextView>(R.id.tvname)
        var email = view.findViewById<TextView>(R.id.tvemail)
        var btn_delete = view.findViewById<Button>(R.id.tvbutton)

        fun bindview(std: StudentModel) {
            id.text = std.id.toString()
            name.text = std.name
            email.text = std.email


        }
    }


}