package com.example.carsharing_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.carsharing_app.Navigation.Navigation
import com.example.carsharing_app.ui.theme.Carsharing_appTheme
import ui_elemente.screens.ProfileScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Carsharing_appTheme {
                Navigation()
            }
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

