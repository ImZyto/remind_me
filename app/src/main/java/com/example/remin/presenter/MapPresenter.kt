package com.example.remin.presenter

import com.example.remin.model.dataclass.Task
import com.example.remin.model.db.AppDatabase
import com.example.remin.model.repository.TasksRepository
import com.example.remin.view.display.MapDisplay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapPresenter(private val display: MapDisplay) {

    private val userDao = AppDatabase.getDatabase(display.getFragmentContext()).tasksDao()
    private val repository = TasksRepository(userDao)

    private var taskList = emptyList<Task>()

    init {
        loadAllTasks()
        display.initMap()
        display.initSearchBar()
    }

    private fun loadAllTasks() = CoroutineScope(Dispatchers.IO).launch {
        taskList = repository.getAllTasks()
        display.loadTaskList(taskList)
    }
}