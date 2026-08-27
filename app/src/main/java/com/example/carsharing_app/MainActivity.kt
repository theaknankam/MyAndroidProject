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
import ui_elemente.navigation.Navigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Move blocking Osmdroid initialization to a background thread
        lifecycleScope.launch {
            Configuration.getInstance().apply {
                load(applicationContext, PreferenceManager.getDefaultSharedPreferences(applicationContext))
                userAgentValue = "CarSharingApp/1.0"
            }
            
            // Ensure Database is initialized
            AppDatabase.getDatabase(this@MainActivity).userDao()
        }

        setContent {
            Navigation()
        }
    }
}
