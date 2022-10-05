package com.example.remin.model.services

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.text.Html
import androidx.core.app.NotificationCompat
import com.example.remin.R
import com.example.remin.model.dataclass.Task
import com.example.remin.view.activity.MainActivity
import java.text.DateFormat
import java.util.*

class TaskNotificationService(private val context: Context) {

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNotification() {
        val activityIntent = Intent(context, MainActivity::class.java)

        val activityPendingIntent = PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        )

        val notification = NotificationCompat.Builder(context, TASK_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Zadanko")
            .setContentText("Tresc zadanka!!! ")
            .setContentIntent(activityPendingIntent)
            .build()

        notificationManager.notify(
            1, notification
        )
    }

    fun showNotification(task: Task) {
        val activityIntent = Intent(context, MainActivity::class.java)

        val activityPendingIntent = PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        )

        val date = DateFormat.getDateInstance(DateFormat.SHORT).format(task.date.time)

        // Notification layout
        val notification = NotificationCompat.Builder(context, TASK_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification_badge)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.ic_notification_icon))
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(context.resources,
                R.drawable.ic_notification_large_picture)).bigLargeIcon(null))
            .setContentTitle(Html.fromHtml(String.format(Locale.getDefault(), "<strong>We want your satisfaction!</strong>")))
            .setContentText("Time for \"${task.name}\" ends on $date 😊")
            .setContentIntent(activityPendingIntent)
            .build()

        notificationManager.notify(
            1, notification
        )
    }

    companion object {
        const val TASK_CHANNEL_ID = "task_channel"
    }
}