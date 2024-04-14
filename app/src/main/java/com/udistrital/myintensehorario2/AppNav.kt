package com.udistrital.myintensehorario2

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.udistrital.myh.Views.HomeScreen
import com.udistrital.myh.Views.LoginScreen
import com.udistrital.myintensehorario2.Views.CreateScreen
import com.udistrital.myintensehorario2.Views.ScheduleListScreen
import com.udistrital.myintensehorario2.Views.ScheduleScreen


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

        composable(route = AppViews.createScreen.route) {
            CreateScreen(navController)
        }

        composable(route = AppViews.schedulListScreen.route) {
            ScheduleListScreen(navController)
        }

        composable(route = AppViews.scheduleScreen.route) {
            ScheduleScreen(navController)
        }
    }
}