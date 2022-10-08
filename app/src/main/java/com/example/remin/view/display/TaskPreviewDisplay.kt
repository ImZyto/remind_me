package com.example.remin.view.display

import android.content.Context

interface TaskPreviewDisplay {

        fun getFragmentContext(): Context

        fun initDatePicker(clickListener: (Int, Int, Int) -> Unit)

        fun showDatePicker()

        fun setTaskDate(date: String)

        fun setTaskPriority(textId: Int)

        fun setName(text: String)

        fun setDescription(text: String)

        fun setLocalization(text: String)

        fun getName(): String

        fun getDescription(): String

        fun getLocalization(): String

        fun setOnDateBtnClickListener(clickListener: () -> Unit)

        fun setOnPrioritySwCheckListener(checkListener: (Boolean) -> Unit)

        fun setOnSubmitButtonClickListener(clickListener: () -> Unit)

        fun navigateBack()
}