package com.udistrital.myintensehorario.Views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness2
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.udistrital.myintensehorario2.R


@Composable
fun SettingsScreen(navController: NavController) {
    Column() {
        HeaderText()
        ProfileCardUI(navController)
        GeneralOptionsUI(navController)
        SupportOptionsUI(navController)
    }
}

@Composable
@Preview
fun SettingsScreenPreview() {
    val navController = rememberNavController()
    SettingsScreen(navController)
}

@Composable
fun HeaderText() {
    Text(
        text = stringResource(id = R.string.Settings),
        //  color = SecondaryColor,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp, bottom = 10.dp),
        fontWeight = FontWeight.ExtraBold,
        fontSize = 16.sp
    )
}

@Composable
fun ProfileCardUI(navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(10.dp),
        elevation = cardElevation(defaultElevation = 4.dp),
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column() {
                Text(
                    text = stringResource(id = R.string.Check_Your_Profile),
                    //color = SecondaryColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )

                Text(
                    text = "Sebas@gmail.com",

                    color = Color.Gray,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                )

                Button(
                    modifier = Modifier.padding(top = 10.dp),
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        //backgroundColor = PrimaryColor
                    ),
                    contentPadding = PaddingValues(horizontal = 30.dp),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 2.dp
                    ),
                    shape = CircleShape
                ) {
                    Text(
                        text = stringResource(id = R.string.View),

                        // color = SecondaryColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

        }
    }
}

@Composable
fun GeneralOptionsUI(navController: NavController) {
    Column(
        modifier = Modifier
            .padding(horizontal = 14.dp)
            .padding(top = 10.dp)
    ) {
        Text(
            text = stringResource(id = R.string.General),

            // color = SecondaryColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(vertical = 8.dp)
        )

        GeneralSettingNotifications(
            icon = {
                Icon(
                    Icons.Filled.Notifications,
                    contentDescription = stringResource(id = R.string.Notifications)

                )
            }
        )
        GeneralSettingTheme(
            icon = {
                Icon(
                    Icons.Filled.Brightness2,
                    contentDescription = stringResource(id = R.string.Dark_Theme)

                )
            }
        )
        GeneralSettingLanguage(
            icon = {
                Icon(
                    Icons.Filled.Language,
                    contentDescription = stringResource(id = R.string.Language)

                )
            }, mainText = stringResource(id = R.string.Language)
        )
    }
}

@Composable
fun GeneralSettingNotifications(icon: @Composable () -> Unit) {
    var checked by remember { mutableStateOf(true) }
    Card(
        modifier = Modifier
            .padding(bottom = 8.dp)
            .fillMaxWidth(),
        elevation = cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            icon() // Icono al principio de la tarjeta
            Spacer(modifier = Modifier.width(14.dp))
            Text(
                text = "Notifications",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(Modifier.weight(1f)) // Esto empujará el Switch hacia la derecha
            Switch(
                checked = checked,
                onCheckedChange = { checked = it }
            )
        }
    }
}

@Composable
fun GeneralSettingTheme(icon: @Composable () -> Unit) {
    var checked by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .padding(bottom = 8.dp)
            .fillMaxWidth(),
        elevation = cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            icon() // Icono al principio de la tarjeta
            Spacer(modifier = Modifier.width(14.dp))
            Text(
                text = "Dark Theme",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(Modifier.weight(1f)) // Esto empujará el Switch hacia la derecha
            Switch(
                checked = checked,
                onCheckedChange = { checked = it }
            )
        }
    }
}

@Composable
fun GeneralSettingLanguage(icon: @Composable () -> Unit, mainText: String) {
    var expanded by remember { mutableStateOf(false) }
    val english = stringResource(id = R.string.English)
    val spanish = stringResource(id = R.string.Spanish)
    val japanese = stringResource(id = R.string.Japanese)
    val options = listOf(english, spanish, japanese)
    Card(
        modifier = Modifier
            .padding(bottom = 8.dp)
            .fillMaxWidth()
            .clickable { expanded = !expanded },
        elevation = cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(shape = CircleShape)
                ) {
                    icon()
                }

                Spacer(modifier = Modifier.width(14.dp))

                Text(
                    text = mainText,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        modifier = Modifier
            .fillMaxWidth()
    ) {
        options.forEach { option ->
            DropdownMenuItem(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                text = {
                    Text(
                        text = option, modifier = Modifier.align(
                            Alignment.CenterHorizontally
                        )
                    )
                }, onClick = {
                    if (option == english) {

                    }
                    if (option == spanish) {

                    }
                    if (option == japanese) {

                    }
                    expanded = false
                }
            )
        }
    }

}

@Composable
fun GeneralSettingItem() {

}

@Composable
fun SupportOptionsUI(navController: NavController) {
    var showDialog by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .padding(horizontal = 14.dp)
            .padding(top = 10.dp)
    ) {
        Text(
            text = stringResource(id = R.string.Support),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(vertical = 8.dp)
        )
        SupportItem(
            icon = {
                Icon(
                    Icons.Filled.Call,
                    contentDescription = stringResource(id = R.string.Contact)
                )
            },
            mainText = stringResource(id = R.string.Contact)
        ) {}
        SupportItem(
            icon = {
                Icon(
                    Icons.Filled.Feedback,
                    contentDescription = stringResource(id = R.string.Feedback)
                )
            },
            mainText = stringResource(id = R.string.Feedback)
        ) {}
        SupportItem(
            icon = {
                Icon(
                    Icons.Filled.Lock,
                    contentDescription = stringResource(id = R.string.Privacy_Policy)
                )
            },
            mainText = stringResource(id = R.string.Privacy_Policy),
            onClick = {
                showDialog = true
            }
        )
        SupportItem(
            icon = {
                Icon(
                    Icons.Filled.Info,
                    contentDescription = stringResource(id = R.string.About)
                )
            },
            mainText = stringResource(id = R.string.About),
            onClick = {

            }
        )


    }
    if (showDialog) {
        ShowDialog(onDismissRequest = { showDialog = false })
    }
}

@Composable
fun ShowDialog(onDismissRequest: () -> Unit) {

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = "No hay privacidad, ya se tu numero de cuenta") },
        text = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "\uD83D\uDD95",
                    fontSize = 70.sp
                )
            }
        },
        confirmButton = {
            Button(onClick = onDismissRequest) {
                Text("OK")
            }
        }
    )
}




@Composable
fun SupportItem(icon: @Composable () -> Unit, mainText: String, onClick: () -> Unit) {
    Card(
        //backgroundColor = Color.White,
        modifier = Modifier
            .padding(bottom = 8.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(shape = CircleShape)
                ) {
                    icon()
                }

                Spacer(modifier = Modifier.width(14.dp))

                Text(
                    text = mainText,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                )
            }

        }
    }
}

