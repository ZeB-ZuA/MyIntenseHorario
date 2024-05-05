package com.udistrital.myintensehorario

sealed class AppViews(val route: String) {
    object loginScreen : AppViews("login_screen")
    object homeScreen : AppViews("home_screen")
    object settingsScreen : AppViews("settings_screen")
    object createScreen : AppViews("create_screen")
    object schedulListScreen : AppViews("schedul_List_screen")
    object scheduleScreen : AppViews("schedule_screen")



}