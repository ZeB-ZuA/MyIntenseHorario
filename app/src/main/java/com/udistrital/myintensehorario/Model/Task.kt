package com.udistrital.myintensehorario.Model
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.udistrital.myintensehorario.TaskReminderReceiver
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

data class Task(
    var id: String = "",
    val name: String = "",
    val start: String = "00:00",
    val finish: String = "00:00",
    val description: String = ""
) {


    override fun toString(): String {
        return "Task(id='$id', name='$name', start='$start', finish='$finish', description='$description')"
    }
}
