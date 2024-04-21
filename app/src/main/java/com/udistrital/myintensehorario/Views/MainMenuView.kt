package com.udistrital.myh.Views


import androidx.compose.foundation.Image
import androidx.compose.foundation.background

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SupervisedUserCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem

import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.udistrital.myintensehorario.AppViews
import com.udistrital.myintensehorario.Views.SettingsScreen
import com.udistrital.myintensehorario2.R
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController) {
    val currentScreen = remember { mutableStateOf("dashboard") }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(stringResource(id = R.string.app_name), modifier = Modifier.padding(16.dp))
                Divider()
                NavigationDrawerItem(
                    shape = RectangleShape,
                    label = { Text(text = stringResource(id = R.string.My_schedule)) },
                    icon = {
                        Icon(
                            Icons.Filled.CalendarToday,
                            contentDescription = stringResource(id = R.string.My_schedules)
                        )
                    },
                    selected = currentScreen.value == "dashboard",
                    onClick = {
                        currentScreen.value = "dashboard"
                        scope.launch { drawerState.close() }
                    }
                )
                Divider()
                NavigationDrawerItem(
                    shape = RectangleShape,
                    label = { Text(text = stringResource(id = R.string.My_settings)) },
                    icon = { Icon(Icons.Filled.Settings, contentDescription = "My schedules") },
                    selected = currentScreen.value == "settings",
                    onClick = {
                        currentScreen.value = "settings"
                        scope.launch { drawerState.close() }
                    }
                )
                Divider()
            }
        }
    ) {
        // Screen content
        when (currentScreen.value) {
            "dashboard" -> DashBoard(navController)
            "settings" -> SettingsScreen(navController)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashBoard(navController: NavController) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Color.White),

        ){
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                containerColor =  colorResource(R.color.green),
                titleContentColor = Color.White,
            ), title = {
                Image(
                    painter = painterResource(id = R.drawable.logofire),
                    contentDescription = "weather",
                    modifier = Modifier.width(50.dp)
                )},
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            modifier = Modifier.size(60.dp),
                            imageVector = Icons.Filled.SupervisedUserCircle,
                            contentDescription = "Localized description",
                            tint = colorResource(R.color.white)
                        )
                    }
                },
            )
            Spacer(Modifier.size(10.dp))
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),

            ) {
                Text(
                    text = stringResource(id = R.string.Hi_User),
                    color =  colorResource(R.color.green),
                    textAlign = TextAlign.Left,
                    fontSize = 30.sp,
                    fontWeight = FontWeight(600),
                )
                Text(
                    text = stringResource(id = R.string.Weather),
                    color =  colorResource(R.color.greenLight),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight(600)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(colorResource(R.color.yellow),),
                    contentAlignment = Alignment.Center
                ){
                    Image(painter = painterResource(id = R.drawable.cloudy), contentDescription = "weather")
                    Text(
                        text = "26°c",
                        fontSize = 27.sp,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontWeight = FontWeight(600)
                        )
                }

                Button(

                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.green)),
                    onClick = {  navController.navigate(AppViews.createScreen.route) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)) {
                    Text(
                        fontSize = 17.sp,
                        text = stringResource(id = R.string.Create_Schedule),
                        textAlign = TextAlign.Center
                    )
                }
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.green)),
                    onClick = {  navController.navigate(AppViews.schedulListScreen.route) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                ) {
                    Text(
                        fontSize = 17.sp,
                        text = stringResource(id = R.string.See_schedules),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
}

fun Text(text: Char) {
    TODO("Not yet implemented")
}


@Composable
@Preview
fun HomeScreenPreview() {
    val navController = rememberNavController()
    HomeScreen(navController)
}