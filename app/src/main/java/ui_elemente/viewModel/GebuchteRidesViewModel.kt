package ui_elemente.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.carsharing_app.data.Trip
import ui_elemente.model.GebuchteRides
import ui_elemente.model.enums.TripStatus
import ui_elemente.model.enums.TripsTab


class GebuchteRidesViewModel : ViewModel() {

    // aktuell ausgewählter Tab
    var selectedTab by mutableStateOf(TripsTab.UPCOMING)
    var upcomingTrips by mutableStateOf(listOf<GebuchteRides>())
    var pastTrips by mutableStateOf(listOf<GebuchteRides>())

    fun onTabSelected(tab: TripsTab) {
        selectedTab = tab
    }

    fun visibleTrips(): List<GebuchteRides> {
        return if (selectedTab == TripsTab.UPCOMING) upcomingTrips else pastTrips
    }

    fun syncFromRoom(trips: List<Trip>) {
        upcomingTrips = trips.map { trip ->
            val dateParts = trip.date.split(" ")
            GebuchteRides(
                id = trip.id.toString(),
                month = dateParts.getOrElse(1) { "" }.uppercase(),
                day = dateParts.getOrElse(0) { "" },
                from = trip.fromCity,
                to = trip.toCity,
                time = "",
                driver = "You",
                status = TripStatus.CONFIRMED
            )
        }
    }
}