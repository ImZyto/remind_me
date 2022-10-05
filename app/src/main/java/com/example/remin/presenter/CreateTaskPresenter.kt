package com.example.remin.presenter

import com.example.remin.R
import com.example.remin.model.dataclass.Task
import com.example.remin.model.db.AppDatabase
import com.example.remin.model.repository.TasksRepository
import com.example.remin.model.services.TaskNotificationService
import com.example.remin.view.display.CreateTaskDisplay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.util.*

class CreateTaskPresenter(private val display: CreateTaskDisplay) {

    private val userDao = AppDatabase.getDatabase(display.getFragmentContext()).tasksDao()
    private val repository = TasksRepository(userDao)

    private var isTaskImportant = false

    private var taskDate = Calendar.getInstance()

    init {
        display.initDatePicker { year, monthOfYear, dayOfMonth ->
            val selectedDate = Calendar.getInstance().apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, monthOfYear)
                set(Calendar.DAY_OF_MONTH, dayOfMonth)
            }

            val date = DateFormat.getDateInstance(DateFormat.SHORT).format(selectedDate.time)
            display.setTaskDate(date)
        }

        display.setOnDateBtnClickListener(display::showDatePicker)
        display.setOnPrioritySwCheckListener(::handlePriorityChanged)
        display.setTaskDate(
            DateFormat.getDateInstance(DateFormat.SHORT).format(taskDate.time)
        )
        display.setOnAddClickListener(::handleAddClick)
    }

    private fun handlePriorityChanged(isImportant: Boolean) {
        isTaskImportant = isImportant
        val textId = if (isImportant)
            R.string.task_priority_high
        else
            R.string.task_priority_normal
        display.setTaskPriority(textId)
    }

    private fun handleAddClick() {
        val task = Task(
            name = display.getName(),
            highPriority = isTaskImportant,
            description = display.getDescription(),
            date = taskDate.time,
            localization = ""
        )
        addTask(task)
        display.navigateBack()
        TaskNotificationService(display.getFragmentContext()).showNotification(task)
    }

    private fun addTask(task: Task) = CoroutineScope(Dispatchers.IO).launch {
        repository.addTask(task)
    }

}