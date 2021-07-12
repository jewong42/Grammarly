package com.jewong.myapplication.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jewong.myapplication.R


class Adapter(
    private var dataSet: List<MainViewModel.Timer>,
    private val onClick: (Int) -> Unit
) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.text_row_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val timer = dataSet[position]
        viewHolder.textView.text = timer.time.toString()
        viewHolder.textView.setOnClickListener {
            onClick.invoke(position)
        }
    }

    fun update(timers: List<MainViewModel.Timer>) {
        dataSet = timers
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

}