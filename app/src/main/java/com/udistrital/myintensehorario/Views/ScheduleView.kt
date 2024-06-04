package com.udistrital.myintensehorario2.Views

import android.app.TimePickerDialog
import android.text.Layout.Alignment
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.udistrital.myintensehorario.AppViews
import com.udistrital.myintensehorario.Model.Day
import com.udistrital.myintensehorario.Model.Schedule
import com.udistrital.myintensehorario.Model.Task
import com.udistrital.myintensehorario.R
import com.udistrital.myintensehorario.Service.ScheduleService
import java.util.Calendar




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen(navController: NavController, id: String?) {
    val scheduleService = ScheduleService()

    var schedule by remember { mutableStateOf<Schedule?>(null) }
    LaunchedEffect(id) {
        schedule = scheduleService.getScheduleById(id)
    }

    println("HORARIO RECIBIDO EN LOS DETALLES" + (schedule?.days ?: ""))

    val items: MutableList<Day> =  mutableListOf()
    if(schedule?.days != null) {
    for (day in schedule?.days!!) {
        items.add(day)
    }}


    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(R.color.green),
                    titleContentColor = Color.White,
                ),
                title = {
                    Text(
                        text = stringResource(R.string.schedule),
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(AppViews.schedulListScreen.route) }) {
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
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()),
            ) {
                Spacer(Modifier.size(50.dp))
                Text(
                    textAlign = TextAlign.Center,
                    text = schedule?.name ?: "Loading..",
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                )
                items.forEach { days ->

                    ViewDays(days)
                    }

                }

            }

        }

    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewDays( day: Day){
   // var selected = remember { mutableStateOf(false) }
    var name = day.name.toString();
    var tasks: MutableList<Task> = day.tasks;
    var selected by remember { mutableStateOf(false) }
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        FilterChip(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = { selected = !selected },
            label = {
                Text(name)
            },
            colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = colorResource(id = R.color.green)
            ),
            selected = selected,
            leadingIcon = if (selected) {
                {
                    Icon(
                        imageVector = Icons.Filled.Done,
                        contentDescription = "Done icon",
                        modifier = Modifier.size(20.dp)
                    )
                }
            } else {
                null
            },
        )
    }
    if(selected) {
        tasks.forEach { task ->
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(1.dp)
                        .border(BorderStroke(1.dp, colorResource(id = R.color.green))),

                    ){
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        Column {
                            Text(text = task.start)
                            Spacer(Modifier.size(3.dp))
                            Text(text =  task.finish)
                        }
                        Spacer(Modifier.size(10.dp))
                        Text(text = task.name)
                    }


                }
                Spacer(Modifier.size(10.dp))
            }
        }

    }
}




