package com.example.carsharing_app

import android.os.Bundle
import android.preference.PreferenceManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.example.carsharing_app.data.AppDatabase
import kotlinx.coroutines.launch
import org.osmdroid.config.Configuration
import ui_elemente.Repository.UserRepository
import ui_elemente.model.User
import ui_elemente.navigation.Navigation

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
                        email = "admin@test.de",
                        isVerified = true,
                        rating = 4.9f,
                        reviewCount = 24,
                        walletBalance = 50.0,
                        co2Saved = 12.5,
                        kmShared = 1200.0
                    )
                )
                repository.insertUser(
                    User(
                        username = "user2",
                        password = "1234",
                        email = "user2@test.de",
                        isVerified = false,
                        rating = 4.5f,
                        reviewCount = 5,
                        walletBalance = 10.0,
                        co2Saved = 2.1,
                        kmShared = 150.0
                    )
                )
            }

            enableEdgeToEdge()
            Configuration.getInstance().apply {
                load(applicationContext, PreferenceManager.getDefaultSharedPreferences(applicationContext))
                userAgentValue = "CarSharingApp/1.0"
            }
            setContent {
                Navigation()
            }
        }
    }
}
