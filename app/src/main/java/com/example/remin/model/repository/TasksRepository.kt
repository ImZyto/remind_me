package com.example.remin.model.repository

import com.example.remin.model.dao.TasksDao
import com.example.remin.model.dataclass.Task

class TasksRepository(private val tasksDao: TasksDao) {

    fun getAllTasks(): List<Task> = tasksDao.getAllTasks()

    fun addTask(task: Task) = tasksDao.addTask(task)
}