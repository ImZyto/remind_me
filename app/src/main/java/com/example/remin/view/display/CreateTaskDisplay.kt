package com.example.remin.view.display

import android.content.Context
import android.widget.TimePicker

interface CreateTaskDisplay {

    fun getFragmentContext(): Context

    fun initDatePicker(clickListener: (Int, Int, Int) -> Unit, timeChangedListener: (Int,Int) -> Unit)

    fun showDatePicker()

    fun setTaskDate(date: String)

    fun setTaskPriority(textId: Int)

    fun getName(): String

    fun getDescription(): String

    fun setOnDateBtnClickListener(clickListener: () -> Unit)

    fun setOnPrioritySwCheckListener(checkListener: (Boolean) -> Unit)

    fun setOnAddClickListener(clickListener: () -> Unit)

    fun navigateBack()
}
