package com.example.remin.presenter

import com.example.remin.model.dataclass.Task
import com.example.remin.model.db.AppDatabase
import com.example.remin.model.repository.TasksRepository
import com.example.remin.view.display.TodoListDisplay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.util.*

class ToDoListPresenter(private val display: TodoListDisplay) {

    private val userDao = AppDatabase.getDatabase(display.getFragmentContext()).tasksDao()
    private val repository = TasksRepository(userDao)

    private var taskList = emptyList<Task>()
    private var taskListFromRepository = emptyList<Task>()

    init {
        loadAllTasks()
        initDatePicker()
        display.setOnCheckedChangeListener(::handleOnChecked)
    }

    private fun handleOnChecked(isChecked: Boolean) {
        if (isChecked)
            handleTrueSelected()
        else
            handleFalseSelected()
    }

    private fun handleTrueSelected() {
        //TODO handle Status layout
    }

    private fun handleFalseSelected() {
        val date = DateFormat.getDateInstance(DateFormat.SHORT).format(Calendar.getInstance().time)
        loadDatePickedTasks(date)
    }

    private fun initDatePicker() {
        display.initDatePicker(::onDatePicked)
        display.setOnDateBtnClickListener(display::showDatePicker)
        val date = DateFormat.getDateInstance(DateFormat.SHORT).format(Calendar.getInstance().time)
        loadDatePickedTasks(date)
    }

    private fun onDatePicked(year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val selectedDate = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, monthOfYear)
            set(Calendar.DAY_OF_MONTH, dayOfMonth)
        }
        val date = DateFormat.getDateInstance(DateFormat.SHORT).format(selectedDate.time)
        loadDatePickedTasks(date)
    }

    private fun loadAllTasks() = CoroutineScope(Dispatchers.IO).launch {
        taskListFromRepository = repository.getAllTasks()
        taskList = taskListFromRepository
        display.loadTaskList(taskList)
    }

    private fun loadDatePickedTasks(date: String) {
        display.setTaskDate(date)
        taskList = taskListFromRepository.filter {
            DateFormat.getDateInstance(DateFormat.SHORT).format(it.date.time) == date
        }

        display.loadTaskList(taskList)
    }

}