package com.example.remin.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.remin.R
import com.example.remin.model.dataclass.Task
import kotlinx.android.synthetic.main.task_list_row.view.*

class TaskAdapter(private val context: Context, private val taskList: ArrayList<Task>) :
    RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.task_list_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.taskNameTv.text = taskList[position].name
        holder.taskPriorityTv.text = setTaskPriority(taskList[position].highPriority)

    }

    private fun setTaskPriority(highPriority: Boolean) =
        if (highPriority)
            context.getString(R.string.priority_high)
        else
            context.getString(R.string.priority_normal)




    override fun getItemCount() = taskList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val taskNameTv = view.taskNameTv
        val taskPriorityTv = view.taskPriorityTv


    }
}