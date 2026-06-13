

package ui_elemente.model

data class User(
    val name: String,
    val rating: Double,
    val reviewCount: Int,
    val car: String,
    val verifiedInfo: String,
    val city: String
)