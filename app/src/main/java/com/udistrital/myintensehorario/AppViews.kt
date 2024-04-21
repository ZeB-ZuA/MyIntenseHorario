package com.udistrital.myintensehorario

sealed class AppViews(val route: String) {
    object loginScreen : AppViews("login_screen")
    object homeScreen : AppViews("home_screen")
    object settingsScreen : AppViews("settings_screen")

}