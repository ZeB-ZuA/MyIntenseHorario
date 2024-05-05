package com.udistrital.myintensehorario2.Views


import android.app.TimePickerDialog
import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.udistrital.myh.Views.SubmitButton
import com.udistrital.myintensehorario.AppViews
import com.udistrital.myintensehorario.R
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateScreen(navController: NavController) {

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
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.padding(innerPadding)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp),

                ) {
                Spacer(Modifier.size(10.dp))
                UserForm(navController)
            }
        }

    }


}

@Composable
fun UserForm(navController: NavController) {
    var scheduleName by rememberSaveable { mutableStateOf("") }

    val items: Array<String> = arrayOf(
        "Lunes",  "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo"
    )

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
            onValueChange = { scheduleName = it },
            placeholder = { Text(text = stringResource(id = R.string.e_g__Oficina)) },
        )

        items.forEach { days ->
            CheckboxDays( days)
        }


        Buttons(navController)

    }
}



@Composable
fun CheckboxDays( day: String){
    var scheduleName by rememberSaveable { mutableStateOf("") }
    val checked = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val calendar = Calendar.getInstance();
    val hour = calendar[Calendar.HOUR_OF_DAY]
    val minute = calendar[Calendar.MINUTE]
    val time = remember {
        mutableStateOf("")
    }
    val timePickerDialog = TimePickerDialog(
        context, { _, hour: Int, minute: Int ->
            time.value = "$hour:$minute"
        }, hour, minute, false
    )
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
    if(checked.value) {
        Column {
            Row() {
                Box(modifier = Modifier
                    .padding(7.dp)
                    .width(90.dp)
                    .background(Color.LightGray)
                ){
                    Column {
                        Text(
                            text = time.value
                        )
                        Spacer(Modifier.size(3.dp))
                        Text(
                            text = time.value
                        )
                    }
                }

                Spacer(Modifier.size(10.dp))
                TextField(
                    modifier = Modifier.width(290.dp),
                    value = scheduleName,
                    onValueChange = { scheduleName = it },
                    placeholder = { Text(text = stringResource(id = R.string.Materia)) },
                )
            }
            Button(onClick = { timePickerDialog.show() }) {
                Text(text = "Hora inicio")
            }
            Button(onClick = { timePickerDialog.show() }) {
                Text(text = "Hora final")
            }
        }
    }
}

@Composable
fun Buttons(navController: NavController){
    val isSubmitOn = false
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SubmitButton(
            textId = stringResource(id = R.string.Save), isOn = isSubmitOn,
            onClick = { navController.navigate(AppViews.homeScreen.route) }
        )

    }
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = { navController.navigate(AppViews.homeScreen.route) },
    ) {
        Text(text = stringResource(id = R.string.Cancel))
    }
}