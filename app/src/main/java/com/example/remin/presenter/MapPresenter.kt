package com.example.remin.presenter

import android.location.Address
import android.os.AsyncTask
import com.example.remin.model.dataclass.Task
import com.example.remin.model.db.AppDatabase
import com.example.remin.model.repository.TasksRepository
import com.example.remin.view.display.MapDisplay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.osmdroid.bonuspack.location.GeocoderNominatim
import java.lang.Exception

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