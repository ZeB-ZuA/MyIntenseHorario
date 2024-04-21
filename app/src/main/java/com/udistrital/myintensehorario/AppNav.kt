package com.udistrital.myintensehorario

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.udistrital.myh.Views.HomeScreen
import com.udistrital.myh.Views.LoginScreen
import com.udistrital.myintensehorario.Views.SettingsScreen


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
        composable(route = AppViews.settingsScreen.route) {
            SettingsScreen(navController)
        }

    }
}