package com.example.carsharing_app.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trips")
data class Trip(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val fromCity: String,
    val toCity: String,
    val price: Int,
    val date: String,
    val seats: Int,
    val status: String = "UPCOMING",
    val createdBy: String = "",
    
    // Preferences (2)
    val allowSmoking: Boolean = false,
    val allowPets: Boolean = false,
    val allowMusic: Boolean = true,
    val ladiesOnly: Boolean = false
)
