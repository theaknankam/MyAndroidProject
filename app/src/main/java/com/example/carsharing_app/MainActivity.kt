package com.example.carsharing_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.carsharing_app.Navigation.Navigation
import com.example.carsharing_app.ui.theme.Carsharing_appTheme
import ui_elemente.screens.Autoauswahl
import ui_elemente.screens.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        setContent {
//            CreateRideScreen()
//            }
        setContent {
            Carsharing_appTheme {

                Navigation()
            }
        }
    }
}