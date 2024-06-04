package com.udistrital.myintensehorario.Model

import com.udistrital.myintensehorario.Model.Enums.DaysEnum


data class Day(
    val name: DaysEnum,
    val tasks: List<Task>

)
