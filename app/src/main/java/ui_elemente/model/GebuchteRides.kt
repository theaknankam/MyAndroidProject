package ui_elemente.model

import ui_elemente.model.enums.TripStatus

class GebuchteRides (
    val id: String,
    val month: String,
    val day: String,
    val from: String,
    val to: String,
    val time: String,
    val driver: String,
    val status: TripStatus
)