package com.udistrital.myintensehorario.Repository

import com.udistrital.myintensehorario.Model.Schedule

interface ScheduleRepository {
    suspend fun save(schedule: Schedule): Boolean
    suspend  fun getScheduleById(id: String): Schedule?
    suspend fun getSchedulesByUserEmail(email: String): List<Schedule>
    suspend fun getSchedulesByUserId(userId: String): List<Schedule>
    suspend fun update(schedule: Schedule): Boolean
    suspend  fun delete(id: String): Boolean
    suspend fun getSchedulesForCurrentUser(): MutableList<Schedule>

}
