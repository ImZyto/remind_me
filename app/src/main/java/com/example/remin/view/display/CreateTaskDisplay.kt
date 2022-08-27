package com.example.remin.view.display

import android.content.Context

interface CreateTaskDisplay {

    fun getFragmentContext(): Context

    fun initView()

    fun initDatePicker(clickListener: (Int, Int, Int) -> Unit)

    fun showDatePicker()

    fun setTaskDate(date: String)

    fun setTaskPriority(textId: Int)

    fun setName(text: String)

    fun setDescription(text: String)

    fun setFragmentTitle(textId: Int)

    fun getName(): String

    fun getDescription(): String

    fun setOnDateBtnClickListener(clickListener: () -> Unit)

    fun setOnPrioritySwCheckListener(checkListener: (Boolean) -> Unit)

    fun setOnSubmitButtonClickListener(clickListener: () -> Unit)

    fun setSubmitButtonText(textId: Int)

    fun navigateBack()
}
