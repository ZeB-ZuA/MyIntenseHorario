package com.udistrital.myintensehorario

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.udistrital.myintensehorario.ui.theme.MyIntenseHorario2Theme

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
