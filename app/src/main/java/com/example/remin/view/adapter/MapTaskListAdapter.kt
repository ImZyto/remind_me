package com.example.remin.view.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.remin.R
import com.example.remin.model.dataclass.Task
import kotlinx.android.synthetic.main.map_task_list_tile.view.*

class MapTaskListAdapter(
    private val context: Context,
    private val taskList: List<Task>,
    private val clickListener: (Task) -> Unit
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
            //clickListener(taskList[position].id)
            Navigation.findNavController(holder.itemView).navigate(R.id.action_mapFragment_to_createTaskFragment, Bundle().apply { putInt("id", taskList[position].id) })
        }
        holder.itemView.setOnClickListener {
            clickListener(taskList[position])
        }
    }


    override fun getItemCount() = taskList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val taskNameTv = view.taskNameTv
        val taskDescriptionTv = view.taskDescriptionTv
        val navigateToTaskBtn = view.navigateToTaskBtn

    }
}