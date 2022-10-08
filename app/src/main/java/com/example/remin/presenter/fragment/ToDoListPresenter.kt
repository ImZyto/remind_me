package com.example.remin.presenter.fragment

import com.example.remin.model.dataclass.Task
import com.example.remin.model.db.AppDatabase
import com.example.remin.model.repository.TasksRepository
import com.example.remin.view.display.TodoListDisplay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ToDoListPresenter(private val display: TodoListDisplay) {

    private val userDao = AppDatabase.getDatabase(display.getFragmentContext()).tasksDao()
    private val repository = TasksRepository(userDao)

    private var taskList = emptyList<Task>()

    init {
        loadAllTasks()
    }

    private fun loadAllTasks() = CoroutineScope(Dispatchers.IO).launch {
        taskList = repository.getAllTasks()
        withContext(Dispatchers.Main) { display.loadTaskList(taskList, display::navigateToEditTaskFragment) }

    }

}