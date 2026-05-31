package com.example.carsharing_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
//import com.example.carsharing_app.ui.theme.Carsharing_appTheme
//import com.example.carsharing_app.ui.theme.screens.Autoauswahl
//import ui_elemente.Navigation.MyApplicationApp
import ui_elemente.screens.CreateRideScreen
import ui_elemente.screens.SearchRideScreen
import ui_elemente.sections.CreateRideForm

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SearchRideScreen()
            }
        }


    }





//    @Composable
//    fun Greeting(name: String, modifier: Modifier = Modifier) {
//        Text(
//            text = "Hello $name!",
//            modifier = modifier
//        )
//
//}
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    Carsharing_appTheme {
//        Greeting("Android")
//    }
//}

