

package ui_elemente.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(

    @PrimaryKey(autoGenerate = true)
    val userId: Int = 0,

    val username: String,
    val password: String,

//    val name: String,
//    val rating: Double,
//    val reviewCount: Int,
//    val car: String,
//    val verifiedInfo: String,
//    val city: String,
    val email: String
)