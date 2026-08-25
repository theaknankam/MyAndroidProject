package com.example.carsharing_app

import android.app.Application
import org.osmdroid.config.Configuration
import android.preference.PreferenceManager

class CarSharingApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Configuration.getInstance().load(
            this,
            PreferenceManager.getDefaultSharedPreferences(this)
        )
        Configuration.getInstance().userAgentValue = packageName
    }
}