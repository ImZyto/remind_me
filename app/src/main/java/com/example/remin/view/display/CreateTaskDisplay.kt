package com.example.remin.view.display

import android.content.Context

interface CreateTaskDisplay {

    fun getFragmentContext(): Context

    fun initDatePicker(clickListener: (Int, Int, Int) -> Unit)

    fun showDatePicker()

    fun initTimePicker(clickListener: (Int, Int) -> Unit)

    fun showTimePicker()

    fun setTaskDate(date: String)

    fun setTaskTime(time: String)

    fun setTaskPriority(textId: Int)

    fun setLocalization(text: String)

    fun getName(): String

    fun getDescription(): String

    fun getLocalization(): String

    fun setOnDateBtnClickListener(clickListener: () -> Unit)

    fun setOnTimeBtnClickListener(clickListener: () -> Unit)

    fun setOnPrioritySwCheckListener(checkListener: (Boolean) -> Unit)

    fun setOnSubmitButtonClickListener(clickListener: () -> Unit)

    fun navigateBack()
}
