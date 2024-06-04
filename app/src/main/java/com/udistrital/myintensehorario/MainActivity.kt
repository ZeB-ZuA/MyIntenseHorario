package com.udistrital.myintensehorario

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.google.firebase.iid.internal.FirebaseInstanceIdInternal
import com.udistrital.myintensehorario.Model.User
import com.udistrital.myintensehorario.Service.ScheduleService
import com.udistrital.myintensehorario.Service.UserService
import com.udistrital.myintensehorario.ui.theme.MyIntenseHorario2Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.Manifest


class MainActivity : ComponentActivity() {

    private val scheduleService = ScheduleService()
    private val taskScheduler = TaskScheduler(this)
    private val requestExactAlarmPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {

            programTaskReminders()
        } else {
            println("Permiso de alarmas exactas denegado")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        taskScheduler.scheduleTestReminder()
        setContent {
            MyIntenseHorario2Theme {
                AppNav()
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            checkExactAlarmPermission()
        } else {

            programTaskReminders()
        }
    }

    private fun checkExactAlarmPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.SCHEDULE_EXACT_ALARM
            ) == PackageManager.PERMISSION_GRANTED -> {

                programTaskReminders()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.SCHEDULE_EXACT_ALARM) -> {
                requestExactAlarmPermissionLauncher.launch(Manifest.permission.SCHEDULE_EXACT_ALARM)
            }
            else -> {
                requestExactAlarmPermissionLauncher.launch(Manifest.permission.SCHEDULE_EXACT_ALARM)
            }
        }
    }

    private fun programTaskReminders() {
        CoroutineScope(Dispatchers.IO).launch {
            val schedules = scheduleService.getSchedulesForCurrentUser()
            TaskScheduler(this@MainActivity).scheduleAllTasks(schedules)
        }
    }
}
