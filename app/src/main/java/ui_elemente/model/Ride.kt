package ui_elemente.model

data class Ride(
    val driverName: String,
    val rating: Double,
    val memberSince: String,
    val from: String,
    val to: String,
    val startTime: String,
    val endTime: String,
    val date: String,
    val seatsLeft: Int,
    val pricePerSeat: Int
)

