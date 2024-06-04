package com.udistrital.myintensehorario.Service

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

import com.udistrital.myintensehorario.Model.Schedule
import com.udistrital.myintensehorario.Repository.ScheduleRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class ScheduleService : ScheduleRepository {

    private val database = FirebaseDatabase.getInstance()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val schedulesRef = database.getReference("schedules")

    override suspend fun save(schedule: Schedule): Boolean {
        val user = auth.currentUser
        return if (user != null) {
            val id = schedulesRef.push().key ?: return false
            val userSchedule = schedule.copy(id = id, userId = user.uid)
            userSchedule.days.forEach { day ->
                day.tasks.forEach { task ->
                    task.id = schedulesRef.child(user.uid).child(id).push().key ?: return false
                }
            }
            try {
                Log.d("ScheduleService", "Saving Schedule: $userSchedule")
                schedulesRef.child(user.uid).child(userSchedule.id).setValue(userSchedule).await()
                true
            } catch (e: Exception) {
                Log.d("Shedule Service Error(Save)", e.localizedMessage)
                false
            }
        } else {
            false
        }
    }


    override suspend fun getScheduleById(id: String?): Schedule? {
        val user = auth.currentUser
        return if (user != null) {
            try {
                val snapshot = id?.let { schedulesRef.child(user.uid).child(it).get().await() }
                snapshot?.getValue(Schedule::class.java)
            } catch (e: Exception) {
                null
            }
        } else {
            null
        }
    }

    override suspend fun getSchedulesByUserEmail(email: String): List<Schedule> {
        TODO("Not yet implemented")
    }


    override suspend fun getSchedulesByUserId(userId: String): List<Schedule> {
        return try {
            val snapshot = schedulesRef.child(userId).get().await()
            snapshot.children.mapNotNull { it.getValue(Schedule::class.java) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun update(schedule: Schedule): Boolean {
        val user = auth.currentUser
        return if (user != null && user.uid == schedule.userId) {
            try {
                schedulesRef.child(user.uid).child(schedule.id).setValue(schedule).await()
                true
            } catch (e: Exception) {
                false
            }
        } else {
            false
        }
    }

    override suspend fun delete(id: String): Boolean {
        val user = auth.currentUser
        return if (user != null) {
            try {
                schedulesRef.child(user.uid).child(id).removeValue().await()
                true
            } catch (e: Exception) {
                false
            }
        } else {
            false
        }
    }

    override suspend fun getSchedulesForCurrentUser(): MutableList<Schedule> {
        val user = auth.currentUser
        return if (user != null) {
            try {
                val snapshot = schedulesRef.child(user.uid).get().await()
                snapshot.children.mapNotNullTo(mutableListOf()) { it.getValue(Schedule::class.java) }
            } catch (e: Exception) {
                Log.d("Shedule Service Error(getSchedulesForCurrentUser)", e.localizedMessage)
                mutableListOf()
            }
        } else {
            mutableListOf()
        }
    }




    fun printSchedulesForCurrentUser() {
        CoroutineScope(Dispatchers.IO).launch {
            val schedules = getSchedulesForCurrentUser()
            schedules.forEach { schedule ->
                println("Schedule ID: ${schedule.id}")
                println("Name: ${schedule.name}")
                println("User ID: ${schedule.userId}")
                schedule.days.forEachIndexed { index, day ->
                    println("Day ${index + 1}: ${day.name}")
                    day.tasks.forEachIndexed { taskIndex, task ->
                        println("Task ${taskIndex + 1} - Name: ${task.name}, Description: ${task.description}")
                        println("   Start: ${task.start}, Finish: ${task.finish}")
                    }
                }
                println()
            }
        }

    }
}
