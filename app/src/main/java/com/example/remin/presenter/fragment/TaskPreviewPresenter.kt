package com.example.remin.presenter.fragment

import com.example.remin.R
import com.example.remin.model.dataclass.Task
import com.example.remin.model.db.AppDatabase
import com.example.remin.model.repository.TasksRepository
import com.example.remin.view.display.TaskPreviewDisplay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.util.*


class TaskPreviewPresenter(private val display: TaskPreviewDisplay, private val task: Task) {

    private val userDao = AppDatabase.getDatabase(display.getFragmentContext()).tasksDao()
    private val repository = TasksRepository(userDao)
    private var isTaskImportant = false

    init {
        initView()
        display.setOnPrioritySwCheckListener(::handlePriorityChanged)
        display.setOnDateBtnClickListener(display::showDatePicker)
        initDatePicker(task.date)
    }

    private fun initView() = task.let {
        display.setName(it.name)
        display.setTaskDate(DateFormat.getDateInstance(DateFormat.SHORT).format(it.date.time))
        display.setDescription(it.description.toString())
        display.setOnSubmitButtonClickListener(::handleSaveClick)
        display.setLocalization(it.localization)
    }

    private fun initDatePicker(taskDate: Date) {
        display.setTaskDate(
            DateFormat.getDateInstance(DateFormat.SHORT).format(taskDate.time)
        )
        display.initDatePicker(::handleDatePicked)
    }

    private fun handleDatePicked(year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val selectedDate = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, monthOfYear)
            set(Calendar.DAY_OF_MONTH, dayOfMonth)
        }

        val date = DateFormat.getDateInstance(DateFormat.SHORT).format(selectedDate.time)
        display.setTaskDate(date)
    }

    private fun handlePriorityChanged(isImportant: Boolean) {
        isTaskImportant = isImportant
        val textId = if (isImportant)
            R.string.task_priority_high
        else
            R.string.task_priority_normal
        display.setTaskPriority(textId)
    }

    private fun handleSaveClick() {
        val updatedTask = Task(
            id = task.id,
            name = display.getName(),
            highPriority = isTaskImportant,
            description = display.getDescription(),
            date = task.date,
            localization = display.getLocalization()
        )
        updateTask(updatedTask)
        display.navigateBack()
    }

    private fun updateTask(task: Task) = CoroutineScope(Dispatchers.IO).launch {
        repository.updateTask(task)
    }
}