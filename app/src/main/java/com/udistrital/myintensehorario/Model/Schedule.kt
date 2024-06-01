package com.udistrital.myintensehorario.Model

import com.udistrital.myintensehorario.Model.Enums.DaysEnum

data class Schedule(
    val id: String,
    val name: String,
    val days: List<Day> = List(7) { Day(DaysEnum.entries[it], emptyList()) }
)