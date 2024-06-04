package com.udistrital.myintensehorario

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.udistrital.myintensehorario.Model.Schedule
import com.udistrital.myintensehorario.Model.Task
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class TaskScheduler(private val context: Context) {

    fun scheduleAllTasks(schedules: List<Schedule>) {
        schedules.forEach { schedule ->
            schedule.days.forEach { day ->
                day.tasks.forEach { task ->
                    scheduleTaskReminder(task)
                }
            }
        }
    }

    fun scheduleTaskReminder(task: Task) {
        val taskStartParts = task.start.split(":")
        val taskStartHour = taskStartParts[0].toInt()
        val taskStartMinute = taskStartParts[1].toInt()
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, TaskReminderReceiver::class.java).apply {
            putExtra("taskName", task.name)
            putExtra("taskDescription", task.description)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            task.id.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val taskStartTime =
            SimpleDateFormat("HH:mm", Locale.getDefault()).parse(task.start)?.time ?: 0
        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, taskStartHour)
            set(Calendar.MINUTE, taskStartMinute - 5)
        }
        val reminderTime = calendar.timeInMillis

        // Imprimir la hora de inicio de la tarea y la hora de la alarma
        Log.d("TaskScheduler", "Hora de inicio de la tarea: ${task.start}")
        Log.d(
            "TaskScheduler",
            "Hora de la alarma: ${DateFormat.getDateTimeInstance().format(Date(reminderTime))}"
        )


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (alarmManager.canScheduleExactAlarms()) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    reminderTime,
                    pendingIntent
                )
            } else {
                // Handle the case where the app cannot schedule exact alarms
            }
        } else {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                reminderTime,
                pendingIntent
            )
        }
    }

    fun scheduleTestReminder() {
        println("Se ejecuta la fun test")
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, TaskReminderReceiver::class.java).apply {
            putExtra("taskName", "Test Task")
            putExtra("taskDescription", "This is a test task.")
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            "testTask".hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            add(Calendar.MINUTE, 1)
        }
        val reminderTime = calendar.timeInMillis

        Log.d("TaskScheduler", "Hora de la alarma prueba: ${DateFormat.getDateTimeInstance().format(Date(reminderTime))}")


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (alarmManager.canScheduleExactAlarms()) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    reminderTime,
                    pendingIntent
                )
            } else {
                // Handle the case where the app cannot schedule exact alarms
            }
        } else {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                reminderTime,
                pendingIntent
            )
        }

    }
}
