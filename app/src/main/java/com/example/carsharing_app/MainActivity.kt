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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.carsharing_app.data.AppDatabase
import com.example.carsharing_app.ui.theme.Carsharing_appTheme
import kotlinx.coroutines.launch
import ui_elemente.Repository.UserRepository
import ui_elemente.model.User
import ui_elemente.navigation.Navigation
import ui_elemente.screens.CreateRideScreen
import ui_elemente.screens.GebuchteRidesScreen
import ui_elemente.screens.ProfileScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {

            val repository = UserRepository(
                AppDatabase.getDatabase(this@MainActivity).userDao()
            )

            if (repository.getUserCount() == 0) {

                repository.insertUser(
                    User(
                        username = "admin",
                        password = "1234",
                        email = "admin@test.de"
                    )
                )
            }
        }

        enableEdgeToEdge()

        setContent {
            Navigation()
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

