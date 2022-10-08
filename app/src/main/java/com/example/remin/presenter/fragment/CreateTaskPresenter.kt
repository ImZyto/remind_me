package com.example.remin.presenter.fragment

import com.example.remin.R
import com.example.remin.model.dataclass.Task
import com.example.remin.model.db.AppDatabase
import com.example.remin.model.repository.TasksRepository
import com.example.remin.model.services.AlarmServices
import com.example.remin.view.display.CreateTaskDisplay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class CreateTaskPresenter(private val display: CreateTaskDisplay) {

    private val userDao = AppDatabase.getDatabase(display.getFragmentContext()).tasksDao()
    private val repository = TasksRepository(userDao)

    private var isTaskImportant = false

    private var taskDate = Calendar.getInstance()

    init {
        display.setOnSubmitButtonClickListener(::handleAddClick)
        display.setOnDateBtnClickListener(display::showDatePicker)
        display.setOnTimeBtnClickListener(display::showTimePicker)
        display.setOnPrioritySwCheckListener(::handlePriorityChanged)
        display.initDatePicker(::handleDatePicked)
        display.initTimePicker(::handleTimePicked)
    }

    private fun handleDatePicked(year: Int, monthOfYear: Int, dayOfMonth: Int) {
        taskDate.apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, monthOfYear)
            set(Calendar.DAY_OF_MONTH, dayOfMonth)
        }
        val date = DateFormat.getDateInstance(DateFormat.SHORT).format(taskDate.time)
        display.setTaskDate(date)
    }

    private fun handleTimePicked(hour: Int, minute: Int) {
        taskDate.apply {
            set(Calendar.HOUR, hour)
            set(Calendar.MINUTE, minute)
        }
        val time = SimpleDateFormat("HH:mm").format(taskDate.time)
        display.setTaskTime(time)
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
            localization = display.getLocalization()
        )
        addTask(task)
        display.navigateBack()
        AlarmServices(display.getFragmentContext()).scheduleTaskNotification(task)
    }

    private fun addTask(task: Task) = CoroutineScope(Dispatchers.IO).launch {
        repository.addTask(task)
    }
}