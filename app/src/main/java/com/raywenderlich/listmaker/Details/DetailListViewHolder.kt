package com.raywenderlich.listmaker.Details

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.listmaker.R

class DetailListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val taskTextView = itemView?.findViewById<TextView>(R.id.titleTask) as TextView


}