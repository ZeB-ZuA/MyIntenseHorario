package com.udistrital.myintensehorario.Model


import java.time.LocalTime


data class Task(
    var id: String = "",
    val name: String = "",
    val start: String = "00:00",
    val finish: String = "00:00",
    val description: String = ""
)

 {
     override fun toString(): String {
         return "Task(id='$id', name='$name', start='$start', finish='$finish', description='$description')"
     }
}
