package ui_elemente.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import ui_elemente.model.GebuchteRides
import ui_elemente.model.enums.TripStatus
import ui_elemente.model.enums.TripsTab


class GebuchteRidesViewModel : ViewModel() {

    // aktuell ausgewählter Tab
    var selectedTab by mutableStateOf(TripsTab.UPCOMING)

    // Liste der kommenden Fahrten
    var upcomingTrips by mutableStateOf(
        listOf(
            GebuchteRides(
                id = "1",
                month = "MAY",
                day = "12",
                from = "Cologne",
                to = "Berlin",
                time = "14:00",
                driver = "John Doe",
                status = TripStatus.CONFIRMED
            ),
            GebuchteRides(
                id = "2",
                month = "MAY",
                day = "20",
                from = "Berlin",
                to = "Hamburg",
                time = "10:00",
                driver = "Anna Smith",
                status = TripStatus.PENDING
            )
        )
    )

    // Liste der vergangenen Fahrten
    var pastTrips by mutableStateOf(listOf<GebuchteRides>())

    fun onTabSelected(tab: TripsTab) {
        selectedTab = tab
    }

    fun visibleTrips(): List<GebuchteRides> {
        return if (selectedTab == TripsTab.UPCOMING) upcomingTrips else pastTrips
    }
}
