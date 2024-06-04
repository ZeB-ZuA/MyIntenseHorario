package com.udistrital.myintensehorario.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udistrital.myintensehorario.Model.Schedule
import com.udistrital.myintensehorario.Service.ScheduleService
import kotlinx.coroutines.launch

class ScheduleListScreenViewModel(private val scheduleService: ScheduleService) : ViewModel() {

    private val _schedules = MutableLiveData<List<Schedule>>()
    val schedules: LiveData<List<Schedule>> = _schedules

    init {
        loadSchedules()
    }

    fun delete(schedule: Schedule){
        viewModelScope.launch {
            try {
                scheduleService.delete(schedule.id)
            }catch (e: Exception){
                Log.e("ScheduleListViewModel", "Error deleting schedule: ${e.message}", e)
            }
        }
    }

    private fun loadSchedules() {
        viewModelScope.launch {
            try {
                val loadedSchedules = scheduleService.getSchedulesForCurrentUser()
                _schedules.value = loadedSchedules
            } catch (e: Exception) {
                Log.e("ScheduleListViewModel", "Error loading schedules: ${e.message}", e)
            }
        }
    }
}
