package com.example.remin.presenter

import com.example.remin.model.dataclass.Task
import com.example.remin.model.db.AppDatabase
import com.example.remin.model.repository.TasksRepository
import com.example.remin.view.display.MapTaskListDisplay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class MapTaskListPresenter (private val display: MapTaskListDisplay) {

    private val userDao = AppDatabase.getDatabase(display.getFragmentContext()).tasksDao()
    private val repository = TasksRepository(userDao)

    private var taskList = emptyList<Task>()

    init {
        loadAllTasks()
    }

    private fun loadAllTasks() = CoroutineScope(Dispatchers.IO).launch {
        taskList = repository.getAllTasks()
        val task1 = Task(id = 1, date = Date(), name = "test", description = "test")
        val task2 = Task(id = 2, date = Date(), name = "test", description = "test")
        val task3 = Task(id = 3, date = Date(), name = "test", description = "test")
        taskList = listOf(task1, task2, task3)
        display.loadTaskList(taskList)
    }

}