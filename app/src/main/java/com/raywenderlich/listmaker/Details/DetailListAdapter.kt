package com.raywenderlich.listmaker.Details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.listmaker.R
import com.raywenderlich.listmaker.TaskList
import com.raywenderlich.listmaker.TodoListViewHolder

class DetailListAdapter(var list: TaskList) : RecyclerView.Adapter<DetailListViewHolder>() {

    interface TodoListClickListener {
        fun listItemClicked(list: TaskList, position: Int)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.detail_list_view_holder, parent, false)
        return DetailListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.tasks.size
    }

    override fun onBindViewHolder(holder: DetailListViewHolder, position: Int) {
        holder.taskTextView?.text = list.tasks[position]


    }
}