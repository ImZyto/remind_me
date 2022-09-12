package com.example.remin.view.display

import android.content.Context
import com.example.remin.model.dataclass.Task

interface TodoListDisplay {

    fun getFragmentContext(): Context

    fun loadTaskList(taskList: List<Task>, itemClickListener: (Task) -> Unit)

    fun navigateToEditTaskFragment(task: Task)
}