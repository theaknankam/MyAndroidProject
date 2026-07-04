package com.example.carsharing_app.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile")
data class ProfileEntity(
        @PrimaryKey
        val id: Int = 1,

        val name: String,
        val email: String,
        val phone: String,
        val city: String,
        val car: String,

        // hier speichern wir den Pfad/Uri vom Bild

        val imageUri: String? = null
    )
