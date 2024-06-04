package com.udistrital.myintensehorario.Model

import com.udistrital.myintensehorario.Model.Enums.DaysEnum

data class Schedule(
    var id: String = "",
    var userId: String? = "",
    val name: String? = "",
    val days: MutableList<Day> = DaysEnum.values().map { Day(it) }.toMutableList()
){
    override fun toString(): String {
        return "Schedule(id='$id', userId='$userId', name='$name', days=$days)"
    }
}