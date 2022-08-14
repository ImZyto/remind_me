package com.example.remin.presenter

import android.app.*
import android.app.Notification
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.remin.R
import com.example.remin.databinding.FragmentCreateTaskBinding
import com.example.remin.model.dataclass.Task
import com.example.remin.model.db.AppDatabase
import com.example.remin.model.repository.TasksRepository
import com.example.remin.view.display.CreateTaskDisplay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.util.*

class CreateTaskPresenter(private val display: CreateTaskDisplay): AppCompatActivity() {

    private val userDao = AppDatabase.getDatabase(display.getFragmentContext()).tasksDao()
    private val repository = TasksRepository(userDao)

    private var isTaskImportant = false

    private var taskDate = Calendar.getInstance()

    private lateinit var binding : FragmentCreateTaskBinding

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = FragmentCreateTaskBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        createNotificationChannel()
//        binding.addBtn.setOnClickListener {
//            scheduleNotification()
//        }
//    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun scheduleNotification() {

        val intent = Intent(display.getFragmentContext().applicationContext, Notification::class.java)
        val title = display.getName()
        val message = display.getDescription()
        intent.putExtra(tittleExtra , title)
        intent.putExtra(messageExtra, message)

        val pendingIntent = PendingIntent.getBroadcast(
            display.getFragmentContext().applicationContext,
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = display.getFragmentContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            taskDate.timeInMillis,
            pendingIntent
        )
    }

//    @RequiresApi(Build.VERSION_CODES.M)
//    private fun getTime(): Long {
//        val minute = binding.timePicker.minute
//        val hour = binding.timePicker.hour
//        val day = binding.datePicker.dayOfMonth
//        val month = binding.datePicker.month
//        val year = binding.datePicker.year
//
//        val calendar = Calendar.getInstance()
//        calendar.set(year, month, day, hour, minute)
//        return calendar.timeInMillis
//    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val name = "Notif Channel"
        val desc = "A Description of the channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID, name, importance)
        channel.description = desc
        val notificationManager = display.getFragmentContext().getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    init {

        createNotificationChannel()

        display.initDatePicker(
            { year, monthOfYear, dayOfMonth ->
                val selectedDate = Calendar.getInstance().apply {
                    set(Calendar.YEAR, year)
                    set(Calendar.MONTH, monthOfYear)
                    set(Calendar.DAY_OF_MONTH, dayOfMonth)
                }

                val date = DateFormat.getDateInstance(DateFormat.SHORT).format(selectedDate.time)
                display.setTaskDate(date)
            },
            { hourOfDay, minute ->
                taskDate.apply {
                    set(Calendar.HOUR_OF_DAY, hourOfDay)
                    set(Calendar.MINUTE, minute)
                }
            }
        )

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
        scheduleNotification()
        display.navigateBack()
    }

    private fun addTask(task: Task) = CoroutineScope(Dispatchers.IO).launch {
        repository.addTask(task)
    }

}