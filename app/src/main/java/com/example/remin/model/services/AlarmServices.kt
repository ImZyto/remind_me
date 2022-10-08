package com.example.remin.model.services

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.remin.model.Constants.EXTRA_TASK
import com.example.remin.model.dataclass.Task

class AlarmServices(private val context: Context) {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager?

    fun checkAlarmsAccess(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            return true
        }
        return alarmManager?.canScheduleExactAlarms() == true
    }

    fun scheduleTaskNotification(task: Task) {
        val alarmIntent = Intent(context, AlarmReceiver::class.java)
        alarmIntent.putExtra(EXTRA_TASK, task)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            22,
            alarmIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager?.set(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            task.date.time,
            pendingIntent
        )

    }

    companion object {
        const val ALARM_CHANNEL_ID = "alarm_channel"
    }
}