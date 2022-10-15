package com.example.remin.view.display

import android.content.Context
import com.example.remin.model.dataclass.Task
import org.osmdroid.util.GeoPoint

interface MapDisplay {

    fun getFragmentContext(): Context

    fun initMap()

    fun addClickListener()

    fun initSearchBar()

    fun loadTaskList(taskList: List<Task>)

    fun navigateToCreateTaskFragment(localization: String)
}