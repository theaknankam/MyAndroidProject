package ui_elemente.model

data class Ride(
    val driverName: String,
    val rating: Double,
    val from: String,
    val to: String,
    val time: String,
    val seatsLeft: Int
)