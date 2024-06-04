package com.udistrital.myintensehorario

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

class TaskReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val taskName = intent.getStringExtra("taskName")
        val taskDescription = intent.getStringExtra("taskDescription")

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(context, MyApp.NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Upcoming Task: $taskName")
            .setContentText(taskDescription)
            .setSmallIcon(R.drawable.notification_icon)
            .setAutoCancel(true)
            .build()
        notificationManager.notify(1, notification) //no tocar este error
    }
}
