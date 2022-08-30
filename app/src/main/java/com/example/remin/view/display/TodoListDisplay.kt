package com.example.remin.view.display

import android.content.Context
import com.example.remin.model.dataclass.Task

interface TodoListDisplay {

    fun getFragmentContext(): Context

    fun loadTaskList(taskList: List<Task>)

    fun setOnDateBtnClickListener(clickListener: () -> Unit)

    fun initDatePicker(clickListener: (Int, Int, Int) -> Unit)

    fun showDatePicker()

    fun navigateToAddTaskFragment()

    fun setOnCheckedChangeListener(checkListener: (Boolean) -> Unit)

    fun setTaskDate(date: String)

    fun setStatusFieldsVisibility(show: Boolean)
}