package com.udistrital.myintensehorario.Model

import com.udistrital.myintensehorario.Model.Enums.DaysEnum


data class Day(
    val name: DaysEnum = DaysEnum.MONDAY,
    var tasks: MutableList<Task> = mutableListOf()
){

    override fun toString(): String {
        return "Day(name=$name, tasks=$tasks)"
    }
}
