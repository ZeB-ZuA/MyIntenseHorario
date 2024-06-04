package com.udistrital.myintensehorario2.Views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.udistrital.myintensehorario.AppViews
import com.udistrital.myintensehorario.Model.Schedule
import com.udistrital.myintensehorario.R
import com.udistrital.myintensehorario.Service.ScheduleService
import com.udistrital.myintensehorario.ViewModel.ScheduleListScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleListScreen(navController: NavController,) {
    val scheduleService = ScheduleService()
    val viewModel = ScheduleListScreenViewModel(scheduleService)
    val schedules by viewModel.schedules.observeAsState(emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(R.color.green),
                    titleContentColor = Color.White,
                ),
                title = {
                    Text(
                        text = stringResource(id = R.string.Schedule_List),
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
    ) { innerPadding ->
        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                schedules.forEach { schedule ->
                    ScheduleCard(schedule = schedule, onClick = {
                        println("ID del horario a detalles" + schedule.id)
                        val action = "${AppViews.scheduleScreen.route}/${schedule.id}"
                        navController.navigate(action)
                    }, onDeleteClick = {
                       
                       viewModel.delete(schedule)
                    },
                        onUpdateClick = {
                            TODO("LOGICA PARA ACTUALIZAR")
                        })
                    Spacer(modifier = Modifier.height(8.dp))
                }
                Button(onClick = { navController.navigate(AppViews.createScreen.route) }) {
                    Text(text = "+")
                }
            }
        }
    }
}

@Composable
fun ScheduleCard(schedule: Schedule, onClick: () -> Unit, onDeleteClick: () -> Unit, onUpdateClick: () -> Unit) {
    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.greenLight),
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
        ) {
            Icon(
                tint = colorResource(id = R.color.black),
                painter = painterResource(R.drawable.calendar),
                contentDescription = ""
            )
            Text(
                text = schedule.name ?: "Unnamed Schedule",
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                fontSize = 22.sp,
                textAlign = TextAlign.Center,
            )

            Spacer(Modifier.weight(1f))

            Icon(
                tint = colorResource(id = R.color.black),
                painter = painterResource(R.drawable.edit_calendar),
                contentDescription = "",
                modifier = Modifier.clickable(onClick = onUpdateClick)
            )
            Spacer(Modifier.width(10.dp))
            Icon(
                tint = colorResource(id = R.color.black),
                painter = painterResource(R.drawable.delete),
                contentDescription = "",
                modifier = Modifier.clickable(onClick = onDeleteClick)
            )
        }
    }
}


