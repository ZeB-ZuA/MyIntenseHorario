package com.udistrital.myh.Views


import android.annotation.SuppressLint
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
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults

import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.google.firebase.auth.FirebaseAuth
import com.udistrital.myintensehorario.AppViews
import com.udistrital.myintensehorario.Views.SettingsScreen
import com.udistrital.myintensehorario.R
import com.udistrital.myintensehorario.Repository.WeatherRetrofitSua
import com.udistrital.myintensehorario.Service.UserService
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController, isDarkTheme: Boolean, onThemeChange: (Boolean) -> Unit) {
    val currentScreen = remember { mutableStateOf("dashboard") }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    text = stringResource(id = R.string.app_name),
                    fontSize = 22.sp,
                    modifier = Modifier.padding(26.dp)
                )
                MyNavigationDrawerItem(
                    label = stringResource(id = R.string.My_schedule),
                    icon = Icons.Filled.CalendarToday,
                    contentDescription = stringResource(id = R.string.My_schedules),
                    selected = currentScreen.value == "dashboard"
                ) {
                    currentScreen.value = "dashboard"
                    scope.launch { drawerState.close() }
                }
                MyNavigationDrawerItem(
                    label = stringResource(id = R.string.My_settings),
                    icon = Icons.Filled.Settings,
                    contentDescription = "My settings",
                    selected = currentScreen.value == "settings"
                ) {
                    currentScreen.value = "settings"
                    scope.launch { drawerState.close() }
                }
            }
        }
    ) {
        // Screen content
        when (currentScreen.value) {
            "dashboard" -> DashBoard(navController)
            "settings" -> SettingsScreen(navController, isDarkTheme = isDarkTheme, onThemeChange = onThemeChange)
        }
    }
}

@Composable
fun MyNavigationDrawerItem(
    label: String,
    icon: ImageVector,
    contentDescription: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    NavigationDrawerItem(
        colors = NavigationDrawerItemDefaults.colors(
            selectedContainerColor = colorResource(id = R.color.greenLight),
        ),
        modifier = Modifier.padding(6.dp),
        label = { Text(text = label, fontWeight = FontWeight(500)) },
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription
            )
        },
        selected = selected,
        onClick = onClick
    )
}



@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashBoard(navController: NavController) {
    val userService = UserService()
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val uid = auth.currentUser?.uid
    val coroutineScope = rememberCoroutineScope()
    var userName by remember { mutableStateOf<String?>(null) }
    var weather by remember { mutableStateOf<String?>(null) }

    coroutineScope.launch {
        userName = uid?.let { userService.getUserName(it) }
        //weather = WeatherRetrofitSua.weatherApi.getWeather().weather[0].main.toString()
    }
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
                    IconButton(onClick = { handleLogOut(navController) }) {
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
                    text = stringResource(id = R.string.Hi_User) + ", "+(userName ?: "loading..."),
                    color =  colorResource(R.color.green),
                    textAlign = TextAlign.Left,
                    fontSize = 30.sp,
                    fontWeight = FontWeight(600),
                )
                Text(
                    text = weather ?: "loading...",
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
                    val weatherImage = when (weather) {
                        "Thunderstorm" -> R.drawable.thunderstorm
                        "Drizzle" -> R.drawable.drizzle
                        "Rain" -> R.drawable.rain
                        "Snow" -> R.drawable.snow
                        "Atmosphere" -> R.drawable.atmosphere
                        "Clear" -> R.drawable.clear
                        "Clouds" -> R.drawable.clouds
                        else -> R.drawable.clear
                    }
                    Image(painter = painterResource(id = weatherImage), contentDescription = "weather"
                    )
                    Text(
                        text =weather ?: "loading...",
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


@Composable
@Preview
fun HomeScreenPreview() {
    val navController = rememberNavController()
   // HomeScreen(navController)
}


fun handleLogOut(navController: NavController) {
    FirebaseAuth.getInstance().signOut()
    navController.navigate(AppViews.loginScreen.route)
}

