package com.udistrital.myintensehorario.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udistrital.myintensehorario.Model.Day
import com.udistrital.myintensehorario.Model.Enums.DaysEnum
import com.udistrital.myintensehorario.Model.Schedule
import com.udistrital.myintensehorario.Model.Task
import com.udistrital.myintensehorario.Service.ScheduleService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class CreateViewModel(private val scheduleService: ScheduleService) : ViewModel() {

    private val _tasksByDay: MutableMap<String, MutableLiveData<List<Task>>> = mutableMapOf()

    private val _scheduleName = MutableLiveData<String>()
    val scheduleName: LiveData<String> = _scheduleName

    fun save() {
        val schedule = _scheduleName.value?.let { updateScheduleWithTasks(it) }
        if (schedule != null) {
            viewModelScope.launch {
                try {
                    val saved = scheduleService.save(schedule = schedule)
                    if (saved) {
                        println("Guardado horario correcto")
                    } else {
                        println("Error en guardado CreateviewModel")
                    }
                } catch (e: Exception) {

                    Log.e("CreateViewModel", "Error al guardar el horario: ${e.message}", e)
                }
            }
        } else {

        }
    }

    private fun updateScheduleWithTasks(scheduleName: String): Schedule {
        val days = DaysEnum.values().map { dayEnum ->
            val dayString = dayEnum.name
            val tasks = _tasksByDay[dayString]?.value?.filterNot { it.isEmpty() } ?: emptyList()
            Day(name = dayEnum, tasks = tasks.toMutableList())
        }.toMutableList()

        return Schedule(name = scheduleName, days = days)
    }

    fun setScheduleName(scheduleName: String) {
        _scheduleName.value = scheduleName
    }

    fun getTasksForDay(day: String): LiveData<List<Task>> {
        if (!_tasksByDay.containsKey(day)) {
            _tasksByDay[day] = MutableLiveData(emptyList())
        }
        return _tasksByDay[day]!!
    }

    fun addTaskForDay(day: String, task: Task) {
        val currentTasks = _tasksByDay[day]?.value?.toMutableList() ?: mutableListOf()
        currentTasks.add(task)
        _tasksByDay[day]?.value = currentTasks
    }

    fun removeTaskForDay(day: String, index: Int) {
        val currentTasks = _tasksByDay[day]?.value?.toMutableList() ?: return
        if (index < currentTasks.size) {
            currentTasks.removeAt(index)
            _tasksByDay[day]?.value = currentTasks
        }
    }

    fun updateTaskName(day: String, index: Int, newName: String) {
        val currentTasks = _tasksByDay[day]?.value?.toMutableList() ?: return
        if (index < currentTasks.size) {
            val updatedTask = currentTasks[index].copy(name = newName)
            currentTasks[index] = updatedTask
            _tasksByDay[day]?.value = currentTasks
        }
    }

    fun updateTaskDescription(day: String, index: Int, newDescription: String) {
        val currentTasks = _tasksByDay[day]?.value?.toMutableList() ?: return
        if (index < currentTasks.size) {
            val updatedTask = currentTasks[index].copy(description = newDescription)
            currentTasks[index] = updatedTask
            _tasksByDay[day]?.value = currentTasks
        }
    }

    fun updateTaskStartTime(day: String, index: Int, newTime: String) {
        val currentTasks = _tasksByDay[day]?.value?.toMutableList() ?: return
        if (index < currentTasks.size) {
            val updatedTask = currentTasks[index].copy(start = newTime)
            currentTasks[index] = updatedTask
            _tasksByDay[day]?.value = currentTasks
        }
    }

    fun updateTaskEndTime(day: String, index: Int, newTime: String) {
        val currentTasks = _tasksByDay[day]?.value?.toMutableList() ?: return
        if (index < currentTasks.size) {
            val updatedTask = currentTasks[index].copy(finish = newTime)
            currentTasks[index] = updatedTask
            _tasksByDay[day]?.value = currentTasks
        }
    }

    fun printAllTasks() {
        _tasksByDay.forEach { (day, tasks) ->
            println("Day: $day, Tasks: ${tasks.value}")
        }
    }

    fun getAllTasksByDay(): Map<String, List<Task>> {
        val allTasksByDay = mutableMapOf<String, List<Task>>()
        _tasksByDay.forEach { (day, tasks) ->
            allTasksByDay[day] = tasks.value ?: emptyList()
        }
        return allTasksByDay
    }
    fun Task.isEmpty(): Boolean {
        return this.name.isBlank() && this.description.isBlank() && this.start == "00:00" && this.finish == "00:00"
    }
}






