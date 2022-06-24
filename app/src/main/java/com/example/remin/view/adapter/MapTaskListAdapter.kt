package com.example.remin.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.remin.R
import com.example.remin.model.dataclass.Task
import kotlinx.android.synthetic.main.map_task_list_tile.view.*

class MapTaskListAdapter(
    private val context: Context,
    private val taskList: List<Task>,
    private val clickListener: (Int) -> Unit
) :
    RecyclerView.Adapter<MapTaskListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.map_task_list_tile, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.taskNameTv.text = taskList[position].name
        holder.taskDescriptionTv.text = taskList[position].description
        holder.navigateToTaskBtn.setOnClickListener {
            clickListener(taskList[position].id)
        }
    }


    override fun getItemCount() = taskList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val taskNameTv = view.taskNameTv
        val taskDescriptionTv = view.taskDescriptionTv
        val navigateToTaskBtn = view.navigateToTaskBtn

    }
}