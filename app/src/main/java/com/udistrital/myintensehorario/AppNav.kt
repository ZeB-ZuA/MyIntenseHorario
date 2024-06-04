package com.udistrital.myintensehorario

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.udistrital.myh.Views.HomeScreen
import com.udistrital.myh.Views.LoginScreen
import com.udistrital.myintensehorario2.Views.CreateScreen
import com.udistrital.myintensehorario2.Views.ScheduleListScreen
import com.udistrital.myintensehorario2.Views.ScheduleScreen
import com.udistrital.myintensehorario.Views.SettingsScreen
import com.udistrital.myintensehorario.ui.theme.MyIntenseHorarioTheme



@Composable
fun MyAppMain() {
    var isDarkTheme by remember { mutableStateOf(false) }

    MyIntenseHorarioTheme(darkTheme = isDarkTheme) {
        AppNav(isDarkTheme = isDarkTheme, onThemeChange = { isDarkTheme = it })
    }
}

@Composable
fun AppNav(isDarkTheme: Boolean, onThemeChange: (Boolean) -> Unit) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppViews.loginScreen.route) {
        composable(route = AppViews.loginScreen.route) {
            LoginScreen(navController)
        }

        composable(route = AppViews.homeScreen.route) {
            HomeScreen(navController, isDarkTheme, onThemeChange)
        }

        composable(route = AppViews.createScreen.route) {
            CreateScreen(navController)
        }

        composable(route = AppViews.schedulListScreen.route) {
            ScheduleListScreen(navController)
        }

        composable(route = AppViews.scheduleScreen.route) {
            ScheduleScreen(navController)
        }

        composable(route = AppViews.settingsScreen.route) {
            SettingsScreen(navController, isDarkTheme, onThemeChange)
        }
    }
}