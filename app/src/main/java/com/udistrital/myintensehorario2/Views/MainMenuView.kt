package com.udistrital.myh.Views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@Composable
fun HomeScreen(navController: NavController) {
    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Text("🔥🔥🔥My Intense Horario🔥🔥🔥", modifier = Modifier.padding(16.dp))
                Divider()
                NavigationDrawerItem(
                    label = { Text(text = "My schedule") },
                    icon = { Icon(Icons.Filled.CalendarToday, contentDescription = "My schedules") },
                    selected = false,
                    onClick = { /*TODO*/ }
                )
                Divider()
                NavigationDrawerItem(
                    label = { Text(text = "My Settings") },
                    icon = { Icon(Icons.Filled.Settings, contentDescription = "My schedules") },
                    selected = false,
                    onClick = { /*TODO*/ }
                )
                Divider()
            }
        }
    ) {
        // Screen content
        DashBoard(navController)
    }

}
@Composable
fun DashBoard(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = "DashBorad",
            textAlign = TextAlign.Center
        )
    }
}


@Composable
@Preview
fun HomeScreenPreview() {
    val navController = rememberNavController()
    HomeScreen(navController)
}