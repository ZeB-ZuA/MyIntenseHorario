package com.udistrital.myh.Views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.udistrital.myintensehorario.AppViews
import com.udistrital.myintensehorario2.R

@Composable
fun LoginScreen(navController: NavController) {
    Surface(modifier = Modifier
        .background(colorResource(R.color.orange))
        .fillMaxSize()) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(painter = painterResource(id = R.drawable.logo), contentDescription = "logo")
            Spacer(Modifier.size(10.dp))
            Text(
                text = stringResource(R.string.Log_In),
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(horizontal = 10.dp)
            )

            UserForm(navController)
        }

    }

}

@Composable
fun UserForm(navController: NavController) {
    val email = rememberSaveable {
        mutableStateOf("")
    }
    val name = rememberSaveable {
        mutableStateOf("")
    }
    val pwd = rememberSaveable {
        mutableStateOf("")
    }
    val isVisiblePwd = rememberSaveable {
        mutableStateOf(false)
    }
    val isSubmitOn = remember(email.value, pwd.value) {
        email.value.trim().isNotEmpty() && pwd.value.trim().isNotEmpty()
    }
    val showSignUpDialog = remember { mutableStateOf(false) }

    val signUpEmail = rememberSaveable {
        mutableStateOf("")
    }
    val signUpName = rememberSaveable {
        mutableStateOf("")
    }
    val signUpPwd = rememberSaveable {
        mutableStateOf("")
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {
        EmailInput(
            emailState = email
        )
        PwdInput(
            pwdState = pwd, labelId = stringResource(R.string.Password), pwdVisible = isVisiblePwd
        )
        SubmitButton(
            textId = stringResource(id = R.string.Log_In), isOn = isSubmitOn,
            onClick = { navController.navigate(AppViews.homeScreen.route) }
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(id = R.string.Dont_have_an_account))
            Text(
                text = stringResource(id = R.string.Sign_up),
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .clickable { showSignUpDialog.value = true },
                color = MaterialTheme.colorScheme.secondary
            )
        }
        if (showSignUpDialog.value) {
            AlertDialog(onDismissRequest = { showSignUpDialog.value = false },
                title = { Text(text = stringResource(id = R.string.registration)) },
                text = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        EmailInput(
                            emailState = signUpEmail
                        )
                        PwdInput(
                            pwdState = signUpPwd,
                            labelId = stringResource(id = R.string.Password),
                            pwdVisible = isVisiblePwd
                        )
                        InputField(
                            valuesState = signUpName,
                            labelId = stringResource(id = R.string.Name),
                            keyboard = KeyboardType.Text
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showSignUpDialog.value = false

                        },
                        enabled = signUpEmail.value.isNotBlank() && signUpPwd.value.isNotBlank() && signUpName.value.isNotBlank()
                    ) {
                        Text(text = stringResource(id = R.string.Sign_up))
                    }
                })
        }

    }
}


@Composable
fun SubmitButton(textId: String, isOn: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(vertical = 3.dp, horizontal = 10.dp)
            .fillMaxWidth(),
        shape = RectangleShape,
        enabled = isOn
    ) {
        Text(
            text = textId, modifier = Modifier.padding(5.dp)
        )
    }
}

@Composable
fun PwdInput(pwdState: MutableState<String>, labelId: String, pwdVisible: MutableState<Boolean>) {
    val visualTransformation = if (pwdVisible.value) {
        VisualTransformation.None
    } else {
        PasswordVisualTransformation()
    }
    OutlinedTextField(value = pwdState.value,
        onValueChange = { pwdState.value = it },
        label = { Text(text = labelId) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        modifier = Modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        visualTransformation = visualTransformation,
        trailingIcon = {
            if (!pwdState.value.isBlank()) {
                PasswordVisibleIcon(pwdVisible)
            }
        })
}

@Composable
fun PasswordVisibleIcon(pwdVisible: MutableState<Boolean>) {
    val image = if (pwdVisible.value) {
        Icons.Default.VisibilityOff
    } else {
        Icons.Default.Visibility
    }
    IconButton(onClick = {
        pwdVisible.value = !pwdVisible.value

    }) {
        Icon(
            imageVector = image, contentDescription = null
        )
    }
}


@Composable
fun EmailInput(
    emailState: MutableState<String>, labelId: String = stringResource(id = R.string.Email)
) {
    InputField(
        valuesState = emailState, labelId = labelId, keyboard = KeyboardType.Email
    )
}

@Composable
fun InputField(
    valuesState: MutableState<String>, labelId: String, keyboard: KeyboardType

) {
    OutlinedTextField(
        value = valuesState.value,
        onValueChange = { valuesState.value = it },
        label = { Text(text = labelId) },
        modifier = Modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboard
        )

    )
}

@Composable
@Preview
fun LoginScreenPreview() {
    val navController = rememberNavController();
    LoginScreen(navController)
}
