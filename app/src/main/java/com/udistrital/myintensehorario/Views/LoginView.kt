package com.udistrital.myh.Views

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.udistrital.myintensehorario.AppViews
import com.udistrital.myintensehorario.R
import com.udistrital.myintensehorario.Service.UserService
import com.udistrital.myintensehorario.ViewModel.LoginViewModel

@Composable
fun LoginScreen(navController: NavController) {
    val userService = UserService()
    val viewModel = LoginViewModel(userService)
    Surface(
        modifier = Modifier
            .background(colorResource(R.color.orange))
            .fillMaxSize()
    ) {

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

            UserForm(navController, viewModel)
        }

    }

}

@Composable
fun UserForm(navController: NavController, viewModel: LoginViewModel) {
    val email: String by viewModel.email.observeAsState(initial = "")
    val pwd: String by viewModel.pwd.observeAsState(initial = "")
    val signUpEmail: String by viewModel.signUpEmail.observeAsState(initial = "")
    val signUpName: String by viewModel.signUpName.observeAsState(initial = "")
    val signUpPwd: String by viewModel.signUpPwd.observeAsState(initial = "")
    val signUpResult by viewModel.signUpResult.observeAsState(initial = false)
    val logInResult by viewModel.logInResult.observeAsState(initial = false)
    val token = "999766775802-okvfs44kbt5fu6q5s995thgelfl5r8qb.apps.googleusercontent.com";
    val context = LocalContext.current
    val isVisiblePwd = rememberSaveable {
        mutableStateOf(false)
    }
    val isSubmitOn = remember(email, pwd) {
        email.trim().isNotEmpty() && pwd.trim().isNotEmpty()
    }
    val showSignUpDialog = remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
       result ->
        handleGoogleSignInResult(result, viewModel, navController)
    }


    LaunchedEffect(signUpResult) {
        if (signUpResult) {
            navController.navigate(AppViews.homeScreen.route)
        }
    }

    LaunchedEffect(logInResult) {
        if (logInResult) {
            navController.navigate(AppViews.homeScreen.route)
        }
    }



    Column(
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {
        EmailInput(
            emailState = email,
            onEmailChange = { viewModel.setEmail(it) }
        )
        PwdInput(
            pwdState = pwd,
            labelId = stringResource(R.string.Password),
            pwdVisible = isVisiblePwd,
            viewModel = viewModel,
            onValueChange = { viewModel.setPwd(it) }
        )
        SubmitButton(
            textId = stringResource(id = R.string.Log_In), isOn = isSubmitOn,
            onClick = {
                viewModel.logIn()
            }
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
            AlertDialog(onDismissRequest = {
                showSignUpDialog.value = false
                viewModel.setSignUpEmail("")
                viewModel.setSignUpPwd("")
                viewModel.setSignUpName("")
            },
                title = { Text(text = stringResource(id = R.string.registration)) },
                text = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        EmailInput(
                            emailState = signUpEmail,
                            onEmailChange = { viewModel.setSignUpEmail(it) }
                        )
                        PwdInput(
                            pwdState = signUpPwd,
                            labelId = stringResource(id = R.string.Password),
                            pwdVisible = isVisiblePwd,
                            viewModel = viewModel,
                            onValueChange = { viewModel.setSignUpPwd(it) }

                        )
                        InputField(
                            valuesState = signUpName,
                            labelId = stringResource(id = R.string.Name),
                            keyboard = KeyboardType.Text,
                            onValueChange = { viewModel.setSignUpName(it) }
                        )
                        GoogleLoginButton() {
                            val signInIntent = getGoogleSignInIntent(context, token)
                            launcher.launch(signInIntent)
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showSignUpDialog.value = false
                            viewModel.printSignUp()
                            viewModel.signUp()
                            viewModel.setSignUpEmail("")
                            viewModel.setSignUpPwd("")
                            viewModel.setSignUpName("")
                        },
                        enabled = signUpEmail.isNotBlank() && signUpPwd.isNotBlank() && signUpName.isNotBlank()
                    ) {
                        Text(text = stringResource(id = R.string.Sign_up))
                    }
                })
        }
        Row(
            modifier = Modifier.padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Divider(
                modifier = Modifier
                    .weight(1f)
                    .height(1.dp)
            )
            Text(
                stringResource(id = R.string.Or),
                modifier = Modifier.padding(horizontal = 4.dp),
                color = MaterialTheme.colorScheme.secondary
            )
            Divider(
                modifier = Modifier
                    .weight(1f)
                    .height(1.dp)
            )
        }
        GoogleLoginButton() {
            val signInIntent = getGoogleSignInIntent(context, token)
            launcher.launch(signInIntent)
        }


    }
}

@Composable
fun GoogleLoginButton(onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        modifier = Modifier
            .padding(vertical = 3.dp, horizontal = 10.dp)
            .fillMaxWidth(),
        shape = RectangleShape,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_google_logo),
            contentDescription = "Google Login",
            modifier = Modifier.size(24.dp),
            tint = Color.Unspecified
        )
        Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
        Text(stringResource(id = R.string.Continue_with_google))
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
fun PwdInput(
    pwdState: String,
    labelId: String,
    pwdVisible: MutableState<Boolean>,
    viewModel: LoginViewModel,
    onValueChange: (String) -> Unit
) {
    val visualTransformation = if (pwdVisible.value) {
        VisualTransformation.None
    } else {
        PasswordVisualTransformation()
    }
    OutlinedTextField(
        value = pwdState,
        onValueChange = onValueChange,
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
            if (!pwdState.isBlank()) {
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
    emailState: String,
    onEmailChange: (String) -> Unit,
    labelId: String = stringResource(id = R.string.Email)
) {
    InputField(
        valuesState = emailState,
        labelId = labelId,
        keyboard = KeyboardType.Email,
        onValueChange = onEmailChange
    )
}

@Composable
fun InputField(
    valuesState: String, onValueChange: (String) -> Unit, labelId: String, keyboard: KeyboardType

) {
    OutlinedTextField(
        value = valuesState,
        onValueChange = onValueChange,
        label = { Text(text = labelId) },
        modifier = Modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboard
        )

    )
}

fun getGoogleSignInIntent(context: Context, token: String): Intent {
    val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(token)
        .requestEmail()
        .build()
    val googleSignInClient = GoogleSignIn.getClient(context, options)
    return googleSignInClient.signInIntent
}
fun handleGoogleSignInResult(result: ActivityResult, viewModel: LoginViewModel, navController: NavController) {
    val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
    try {
        val account = task.getResult(ApiException::class.java)
        val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
        viewModel.googleLogIn(credential) {
            navController.navigate(AppViews.homeScreen.route)
        }
    } catch (e: ApiException) {
        Log.d("Auth", "Error google SignUP ${e.localizedMessage}")
    }
}





@Composable
@Preview(showBackground = true, showSystemUi = true)
fun LoginScreenPreview() {
    val navController = rememberNavController();
    LoginScreen(navController)
}
