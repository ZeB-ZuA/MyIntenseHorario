package com.udistrital.myintensehorario2

sealed class AppViews(val route: String) {
    object loginScreen : AppViews("login_screen")
    object homeScreen : AppViews("home_screen")
    object createScreen : AppViews("create_screen")
    object schedulListScreen : AppViews("schedule_list_screen")
    object scheduleScreen : AppViews("schedule_screen")

}