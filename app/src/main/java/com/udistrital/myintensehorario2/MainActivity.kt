package com.udistrital.myintensehorario2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.udistrital.myh.Views.HomeScreen
import com.udistrital.myh.Views.LoginScreen
import com.udistrital.myintensehorario2.ui.theme.MyIntenseHorario2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyIntenseHorario2Theme {
              AppNav()
            }
        }
    }
}
