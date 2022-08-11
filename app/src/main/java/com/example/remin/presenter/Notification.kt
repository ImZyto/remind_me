package com.example.remin.presenter

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import com.example.remin.R
import com.example.remin.view.activity.MainActivity


const val notificationID = 1
const val channelID = "channel1"
const val tittleExtra = "titleExtra"
const val messageExtra = "messageExtra"

class Notification : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val intentToActivity = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intentToActivity, 0)

//        val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.ic_icon_256x256)
        val bitmapLargeIcon = BitmapFactory.decodeResource(context.resources, R.drawable.ic_icon_256x256)

        val notification = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setLargeIcon(bitmapLargeIcon)
//            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
            .setContentTitle(intent.getStringExtra(tittleExtra)) // to nie dziala przez intent
            .setContentText(intent.getStringExtra(messageExtra)) //to też
            .setContentIntent(pendingIntent)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notificationID, notification)
    }

}