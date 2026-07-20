package ui_elemente.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val userId: Int = 0,
    val username: String,
    val password: String,
    val email: String,
    
    // Safety & Trust (1)
    val isVerified: Boolean = false,
    val rating: Float = 5.0f,
    val reviewCount: Int = 0,
    
    // Payment (4)
    val walletBalance: Double = 0.0,
    
    // Sustainability (5)
    val co2Saved: Double = 0.0, // in kg
    val kmShared: Double = 0.0
)
