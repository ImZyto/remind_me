package com.example.remin.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.remin.R
import com.example.remin.model.dataclass.Task
import kotlinx.android.synthetic.main.task_list_row.view.*
import java.text.DateFormat

class TaskAdapter(private val context: Context, private val taskList: List<Task>, private val itemClickListener: (Int) -> Unit) :
    RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.task_list_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener { itemClickListener(taskList[position].id) }
        holder.taskNameTv.text = taskList[position].name
        holder.taskPriorityTv.text = setTaskPriority(taskList[position].highPriority)
        holder.taskDescriptionTv.text = taskList[position].description
        holder.taskLocalizationTv.text = taskList[position].localization
        holder.taskDateTv.text = DateFormat.getDateTimeInstance().format(taskList[position].date)
        holder.taskExpandGrp.visibility = GONE
        holder.taskExpandBtn.setOnClickListener {
            val transition =
                if (holder.taskExpandGrp.visibility == GONE)
                    VISIBLE
                else
                    GONE

            holder.taskExpandGrp.visibility = transition
        }
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
        val taskDescriptionTv = view.taskDescriptionTv
        val taskDateTv = view.taskDateTv
        val taskLocalizationTv = view.taskLocalizationTv
        val taskExpandBtn = view.taskExpandBtn
        val taskExpandGrp = view.taskExpandGroup

    }
}