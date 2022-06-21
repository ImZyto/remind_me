package com.example.remin.view.display

import android.content.Context
import com.example.remin.model.dataclass.Task

interface MapTaskListDisplay {

    fun getFragmentContext(): Context

    fun loadTaskList(taskList: List<Task>)

}