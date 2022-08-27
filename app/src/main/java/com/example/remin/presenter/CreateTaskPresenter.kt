package com.example.remin.presenter

import com.example.remin.R
import com.example.remin.model.dataclass.Task
import com.example.remin.model.db.AppDatabase
import com.example.remin.model.repository.TasksRepository
import com.example.remin.view.display.CreateTaskDisplay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.DateFormat
import java.util.*

class CreateTaskPresenter(private val display: CreateTaskDisplay) {

    private val userDao = AppDatabase.getDatabase(display.getFragmentContext()).tasksDao()
    private val repository = TasksRepository(userDao)

    private var isTaskImportant = false

    private var taskDate = Calendar.getInstance()

    private lateinit var task: Task

    init {
        display.initView()
        display.setOnDateBtnClickListener(display::showDatePicker)
        display.setOnPrioritySwCheckListener(::handlePriorityChanged)
        display.initDatePicker { year, monthOfYear, dayOfMonth ->
            val selectedDate = Calendar.getInstance().apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, monthOfYear)
                set(Calendar.DAY_OF_MONTH, dayOfMonth)
            }

            val date = DateFormat.getDateInstance(DateFormat.SHORT).format(selectedDate.time)
            display.setTaskDate(date)
        }
    }

    fun initViewAsEditTaskView(taskId: Int) = CoroutineScope(Dispatchers.IO).launch {
        task = repository.getTaskById(taskId)
        withContext(Dispatchers.Default) {
            display.setDescription(task.description?: "")
            display.setName(task.name)
            display.setFragmentTitle(R.string.create_task_edit)
            display.setTaskDate(DateFormat.getDateInstance(DateFormat.SHORT).format(task.date.time))
            display.setSubmitButtonText(R.string.common_save)
            display.setOnSubmitButtonClickListener(::handleSaveClick)
        }
    }

    fun initViewAsAddTaskView() {
        display.setOnSubmitButtonClickListener(::handleAddClick)
    }

    private fun handlePriorityChanged(isImportant: Boolean) {
        isTaskImportant = isImportant
        val textId = if (isImportant)
            R.string.priority_high
        else
            R.string.priority_normal
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
    }

    private fun handleSaveClick() {
        //TODO: implement entity update on database
    }

    private fun addTask(task: Task) = CoroutineScope(Dispatchers.IO).launch {
        repository.addTask(task)
    }

}