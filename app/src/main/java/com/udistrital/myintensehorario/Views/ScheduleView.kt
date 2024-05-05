package com.udistrital.myintensehorario2.Views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.udistrital.myintensehorario.AppViews
import com.udistrital.myintensehorario.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen(navController: NavController) {
    val items: Array<String> = arrayOf(
        "Lunes",  "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo"
    )
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(R.color.green),
                    titleContentColor = Color.White,
                ),
                title = {
                    Text(
                        text = stringResource(id = R.string.Office),
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(AppViews.homeScreen.route) }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Localized description",
                            tint = Color.White
                        )
                    }
                },

                )
        }
    ){ innerPadding ->
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.padding(40.dp)
                    .verticalScroll(rememberScrollState()),
            ) {
                Spacer(Modifier.size(10.dp))
                items.forEach { days ->
                   // CheckboxDays( days)
                    Column {
                        Text(text = days,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            modifier = Modifier
                                .padding(16.dp),)
                        Row {
                            Column {
                                Text(text = "01:00 pm")
                                Text(text =  "03:00 pm")
                            }
                            Spacer(Modifier.size(15.dp))
                            Text(text = "Fisica")
                        }
                        
                    }

                }

            }

        }

    }}


