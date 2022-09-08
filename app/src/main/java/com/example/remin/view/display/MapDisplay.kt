package com.example.remin.view.display

import android.content.Context
import com.example.remin.model.dataclass.Task

interface MapDisplay {

    fun getFragmentContext(): Context

    fun initMap()

    fun addClickListener()

    fun initSearchBar()

    fun loadTaskList(taskList: List<Task>)
}