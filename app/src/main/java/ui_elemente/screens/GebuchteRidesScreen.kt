package ui_elemente.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ui_elemente.components.TabItem
import ui_elemente.model.enums.TripsTab
import ui_elemente.components.TripCard
import ui_elemente.viewModel.GebuchteRidesViewModel


@Composable
fun GebuchteRidesScreen(
    viewModel: GebuchteRidesViewModel = viewModel(),
    navController: NavController
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Text(
            text = "My Trips",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, bottom = 16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            TabItem(
                text = "Upcoming",
                selected = viewModel.selectedTab == TripsTab.UPCOMING,
                modifier = Modifier.weight(1f)
            ) { viewModel.onTabSelected(TripsTab.UPCOMING) }

            TabItem(
                text = "Past",
                selected = viewModel.selectedTab == TripsTab.PAST,
                modifier = Modifier.weight(1f)
            ) { viewModel.onTabSelected(TripsTab.PAST) }
        }

        Spacer(modifier = Modifier.height(16.dp))

        val trips = viewModel.visibleTrips()

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
                items(trips, key = { it.id }) { trip ->
                    TripCard(
                        ride = trip,
                        onClick = {
                            navController.navigate("rideDetails/${trip.id}")
                        }
                    )
                }
            }
        }
    }
}