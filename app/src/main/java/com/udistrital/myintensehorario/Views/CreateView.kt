package com.udistrital.myintensehorario2.Views

import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.udistrital.myh.Views.SubmitButton
import com.udistrital.myintensehorario.AppViews
import com.udistrital.myintensehorario.Model.Day
import com.udistrital.myintensehorario.Model.Enums.DaysEnum
import com.udistrital.myintensehorario.Model.Schedule
import com.udistrital.myintensehorario.Model.Task
import com.udistrital.myintensehorario.R
import com.udistrital.myintensehorario.Service.ScheduleService
import com.udistrital.myintensehorario.TaskScheduler
import com.udistrital.myintensehorario.ViewModel.CreateViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.Calendar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateScreen(navController: NavController) {
    val scheduleService = ScheduleService()
    val viewModel = CreateViewModel(scheduleService)
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(R.color.green),
                    titleContentColor = Color.White,
                ),
                title = {
                    Text(
                        text = stringResource(id = R.string.Create_Schedule),
                        modifier = Modifier.padding(horizontal = 10.dp)
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
                }
            )
        }
    ) { innerPadding ->
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Spacer(Modifier.size(10.dp))
                UserForm(navController, viewModel)
            }
        }
    }
}



@Composable
@Preview(showBackground = true, showSystemUi = true)
fun CreateScreenPreview() {
    val navController = rememberNavController()
    CreateScreen(navController)
}


@Composable
fun UserForm(navController: NavController, viewModel: CreateViewModel) {

    val scheduleName by viewModel.scheduleName.observeAsState(initial = "")
    val items = DaysEnum.values().toList()
    val scheduleService = ScheduleService()
    val context = LocalContext.current
    Column(
        modifier = Modifier.padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Text(
            text = stringResource(id = R.string.Name),
            style = MaterialTheme.typography.bodyLarge
        )

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = scheduleName,
            onValueChange = { viewModel.setScheduleName(it)},
            placeholder = { Text(text = stringResource(id = R.string.e_g__Oficina)) },
        )

        items.forEach { day ->
            CheckboxDays(day.toString(), viewModel )
        }
        Buttons(
            navController,
            onClick = {

                viewModel.printAllTasks()
                CoroutineScope(Dispatchers.IO).launch {
                    TaskScheduler(context).scheduleAllTasks(scheduleService.getSchedulesForCurrentUser())
                 viewModel.save()
                }
            },
            onClick2 = {
                scheduleService.printSchedulesForCurrentUser()
                navController.navigate(AppViews.homeScreen.route)
            }
        )


    }
}

@Composable
fun CheckboxDays(day: String, viewModel: CreateViewModel) {
    val checked = remember { mutableStateOf(false) }
    val tasks = viewModel.getTasksForDay(day).observeAsState()

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked.value,
            onCheckedChange = { checked.value = it },
        )
        Text(day)
    }

    if (checked.value) {
        Column {
            tasks.value?.forEachIndexed { index, task ->
                TaskRow(
                    taskName = task.name,
                    taskDescription = task.description,
                    startTime = task.start,
                    endTime = task.finish,
                    onTaskNameChange = { newName ->
                        viewModel.updateTaskName(day, index, newName)
                    },
                    onTaskDescriptionChange = { newDescription ->
                        viewModel.updateTaskDescription(day, index, newDescription)
                    },
                    onStartTimeChange = { newTime ->
                        viewModel.updateTaskStartTime(day, index, newTime)
                    },
                    onEndTimeChange = { newTime ->

                        viewModel.updateTaskEndTime(day, index, newTime)
                    },
                    onRemoveTask = {
                        viewModel.removeTaskForDay(day, index)
                        viewModel.printAllTasks()
                    }
                )
            }

            Button(onClick = {
                viewModel.addTaskForDay(day, Task())
                viewModel.printAllTasks()
            }) {
                Text(text = "+")
            }
        }
    }
}




@Composable
fun Buttons(navController: NavController, onClick: () -> Unit, onClick2: () -> Unit) {
    val isSubmitOn = true
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SubmitButton(
            textId = stringResource(id = R.string.Save), isOn = isSubmitOn,
            onClick = { onClick() }
        )

    }
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = { onClick2() },
    ) {
        Text(text = stringResource(id = R.string.Cancel))
    }
}

@Composable
fun TaskRow(
    taskName: String,
    taskDescription: String,
    startTime: String,
    endTime: String,
    onTaskNameChange: (String) -> Unit,
    onTaskDescriptionChange: (String) -> Unit,
    onStartTimeChange: (String) -> Unit,
    onEndTimeChange: (String) -> Unit,
    onRemoveTask: () -> Unit
) {
    val context = LocalContext.current

    val startTimePickerDialog = createTimePickerDialog(context, startTime) { time ->
        onStartTimeChange(time)
    }

    val endTimePickerDialog = createTimePickerDialog(context, endTime) { time ->
        onEndTimeChange(time)
    }

    Row {
        Box(
            modifier = Modifier
                .padding(1.dp)
                .width(95.dp)
        ) {
            Column {
                Button(
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .height(26.dp)
                        .padding(0.dp),
                    onClick = { startTimePickerDialog.show() }
                ) {
                    Text(text = startTime)
                }
                Spacer(Modifier.size(5.dp))
                Button(
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .height(26.dp)
                        .padding(0.dp),
                    onClick = { endTimePickerDialog.show() }
                ) {
                    Text(text = endTime)
                }
            }
        }
        Spacer(Modifier.size(8.dp))
        Column {
            TextField(
                modifier = Modifier.width(290.dp),
                value = taskName,
                onValueChange = onTaskNameChange,
                placeholder = { Text(text = stringResource(id = R.string.Materia)) },
            )
            TextField(
                modifier = Modifier.width(290.dp),
                value = taskDescription,
                onValueChange = onTaskDescriptionChange,
                placeholder = { Text(text = "Description") },
            )
            Button(onClick = onRemoveTask) {
                Text(text = "Remove")
            }
        }
    }
}

fun createTimePickerDialog(
    context: Context,
    initialTime: String,
    onTimeSet: (String) -> Unit
): TimePickerDialog {
    val (initialHour, initialMinute) = initialTime.split(":").map { it.toInt() }
    return TimePickerDialog(
        context, { _, selectedHour: Int, selectedMinute: Int ->
            onTimeSet(String.format("%02d:%02d", selectedHour, selectedMinute))
        }, initialHour, initialMinute, true
    )
}
