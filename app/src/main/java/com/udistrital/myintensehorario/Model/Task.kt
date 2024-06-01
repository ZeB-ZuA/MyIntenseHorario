package com.udistrital.myintensehorario.Model


import java.time.LocalDateTime


data class Task(
    val id: String,
    val name: String,
    val start: LocalDateTime,
    val finish: LocalDateTime,
    val description: String
)

 {

}
