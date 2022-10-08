package com.example.remin.model.services

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.SystemClock
import androidx.core.content.ContextCompat.getSystemService

class AlarmServices(private val context: Context) {

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager?

    fun checkAlarmsAccess() : Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S){
            return true
        }
        return alarmManager?.canScheduleExactAlarms() == true
    }

    fun getInstance(){
        val alarmIntent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            22,
            alarmIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager?.set(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime()+10000,
            pendingIntent
        )

    }
    companion object {
        const val ALARM_CHANNEL_ID = "alarm_channel"
    }



}