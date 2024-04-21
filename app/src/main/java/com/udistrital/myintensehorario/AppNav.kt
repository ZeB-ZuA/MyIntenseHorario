package com.udistrital.myintensehorario

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.udistrital.myh.Views.HomeScreen
import com.udistrital.myh.Views.LoginScreen
<<<<<<< HEAD:app/src/main/java/com/udistrital/myintensehorario2/AppNav.kt
import com.udistrital.myintensehorario2.Views.CreateScreen
import com.udistrital.myintensehorario2.Views.ScheduleListScreen
import com.udistrital.myintensehorario2.Views.ScheduleScreen
=======
import com.udistrital.myintensehorario.Views.SettingsScreen
>>>>>>> feature/SettingsScreen:app/src/main/java/com/udistrital/myintensehorario/AppNav.kt


@Composable
fun AppNav() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppViews.loginScreen.route) {
        composable(route = AppViews.loginScreen.route) {
            LoginScreen(navController)
        }

        composable(route = AppViews.homeScreen.route) {
            HomeScreen(navController)
        }
<<<<<<< HEAD:app/src/main/java/com/udistrital/myintensehorario2/AppNav.kt

        composable(route = AppViews.createScreen.route) {
            CreateScreen(navController)
        }

        composable(route = AppViews.schedulListScreen.route) {
            ScheduleListScreen(navController)
        }

        composable(route = AppViews.scheduleScreen.route) {
            ScheduleScreen(navController)
        }
=======
        composable(route = AppViews.settingsScreen.route) {
            SettingsScreen(navController)
        }

>>>>>>> feature/SettingsScreen:app/src/main/java/com/udistrital/myintensehorario/AppNav.kt
    }
}