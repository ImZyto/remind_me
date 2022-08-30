package com.example.remin.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.remin.R
import com.example.remin.model.dataclass.Task
import kotlinx.android.synthetic.main.task_list_row.view.*

class TaskAdapter(private val context: Context, private val taskList: List<Task>) :
    RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.task_list_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(taskList[position])
    }

    private fun setTaskStatusString(isDone: Boolean): Int =
        if (isDone)
            R.string.task_status_done
        else
            R.string.task_status_not_done


    override fun getItemCount() = taskList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val taskNameTv: TextView = view.taskNameTv
        val taskStatusTv: TextView = view.taskStatusTv

        fun bindView(task: Task) {
            taskNameTv.text = task.name
            taskStatusTv.text = context.getString(setTaskStatusString(task.isDone))
            taskStatusTv.isActivated = task.isDone
        }
    }
}