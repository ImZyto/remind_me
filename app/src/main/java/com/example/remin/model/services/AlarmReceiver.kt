package com.example.remin.model.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.remin.R
import com.example.remin.model.services.AlarmServices.Companion.ALARM_CHANNEL_ID
import com.example.remin.view.activity.MainActivity
import java.util.Calendar

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, p1: Intent?) {
        p1?.let {
            handleAlaramData(context, it)
            Log.d("alarm", "notification")
        }
        Log.d("alarm", "on receive")
    }

    private fun handleAlaramData(context: Context?, intent: Intent) {

        context?.let {

            val title = intent.getStringExtra("title")
            val description = intent.getStringExtra("desp")

            createNotificationChannel(context = it)

            createNotification(
                context = it,
                title = "dupaaa",
                description = "I chuj",
                id = 50,
                subscriptionId = 50
            )
        }

    }

    private fun createNotification(
        context: Context,
        title : String,
        description : String,
        id : Int,
        subscriptionId: Long
    ) {
        // Create an explicit intent for an Activity in your app
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(context, ALARM_CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(description)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            // Set the intent that will fire when the user taps the notification
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(Calendar.getInstance().timeInMillis.toInt(), builder.build())
        }
    }

    private fun createNotificationChannel(context: Context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val importance = NotificationManager.IMPORTANCE_MAX
            val channel = NotificationChannel(ALARM_CHANNEL_ID, "CHANNEL_NAME", importance).apply {
                description = "CHANNEL_DESP"
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }
    }
}