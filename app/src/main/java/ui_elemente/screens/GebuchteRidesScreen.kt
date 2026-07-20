package ui_elemente.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.carsharing_app.data.TripViewModel
import ui_elemente.components.TabItem
import ui_elemente.model.enums.TripsTab
import ui_elemente.components.TripCard
import ui_elemente.model.GebuchteRides
import ui_elemente.model.enums.TripStatus
import ui_elemente.navigation.Topbar
import ui_elemente.viewModel.GebuchteRidesViewModel


@Composable
fun GebuchteRidesScreen(
    viewModel: TripViewModel = viewModel(),
    navController: NavHostController
) {
    val bookedTrips by viewModel.bookedTrips.collectAsState()
    var selectedTab by remember { mutableStateOf(TripsTab.UPCOMING) }

    val trips = if (selectedTab == TripsTab.UPCOMING)
        bookedTrips.filter { it.status == "UPCOMING" }
    else
        bookedTrips.filter { it.status == "PAST" }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Topbar("My Trips", navController)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            TabItem(
                text = "Upcoming",
                selected = selectedTab == TripsTab.UPCOMING,
                modifier = Modifier.weight(1f)
            ) { selectedTab = TripsTab.UPCOMING }

            TabItem(
                text = "Past",
                selected = selectedTab == TripsTab.PAST,
                modifier = Modifier.weight(1f)
            ) {selectedTab = TripsTab.PAST }
        }

        Spacer(modifier = Modifier.height(16.dp))

      //  val trips = viewModel.visibleTrips()

        if (trips.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 48.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "No trips here yet", color = Color.Gray)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(trips, key = { it.id }) { trip ->     val dateParts = trip.date.split(" ")
                    TripCard(
                        ride = GebuchteRides(
                            id = trip.id.toString(),
                            month = dateParts.getOrElse(1) { "" }.uppercase(),
                            day = dateParts.getOrElse(0) { "" },
                            from = trip.fromCity,
                            to = trip.toCity,
                            time = "",
                            driver = trip.createdBy,
                            status = TripStatus.CONFIRMED
                        ),
                          onClick={  navController.navigate("rideDetails/${trip.id}/true")
                        }
                    )
                }
            }
        }
    }
}