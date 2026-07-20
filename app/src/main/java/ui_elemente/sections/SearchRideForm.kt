package ui_elemente.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.carsharing_app.data.TripViewModel
import com.google.firebase.auth.FirebaseAuth
import ui_elemente.components.DatePickerField
import ui_elemente.components.LocationInput
import ui_elemente.components.PriceField
import ui_elemente.components.PrimaryButton
import ui_elemente.components.SeatSelector
import ui_elemente.components.TripCard
import ui_elemente.model.GebuchteRides
import ui_elemente.model.enums.TripStatus
import ui_elemente.navigation.Topbar

@Composable
fun SearchRideForm(
    viewModel: TripViewModel = viewModel(),
    navController: NavHostController,
) {
    var location1 by remember { mutableStateOf("") }
    var location2 by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    
    // Preference Filters (2)
    var filterSmoking by remember { mutableStateOf(false) }
    var filterPets by remember { mutableStateOf(false) }

    val firestoreTrips = viewModel.firestoreTrips
    val currentUserId = FirebaseAuth.getInstance().currentUser?.uid

    val filteredTrips = firestoreTrips.filter { trip ->
        trip.createdBy != currentUserId &&
                (location1.isEmpty() || trip.fromCity.contains(location1, ignoreCase = true)) &&
                (location2.isEmpty() || trip.toCity.contains(location2, ignoreCase = true)) &&
                (date.isEmpty() || trip.date == date) &&
                (!filterSmoking || trip.allowSmoking) &&
                (!filterPets || trip.allowPets)
    }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Topbar("Search Ride", navController)

        LocationInput(label = "From", value = location1, onValueChange = { location1 = it })
        LocationInput(label = "To", value = location2, onValueChange = { location2 = it })
        DatePickerField(modifier = Modifier.fillMaxWidth(), value = date, onValueChange = { date = it })

        // Quick Filters (2)
        Text("Quick Filters", style = MaterialTheme.typography.labelLarge)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterChip(
                selected = filterSmoking,
                onClick = { filterSmoking = !filterSmoking },
                label = { Text("Smoking Allowed") }
            )
            FilterChip(
                selected = filterPets,
                onClick = { filterPets = !filterPets },
                label = { Text("Pets Allowed") }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Rides Found (${filteredTrips.size})", fontSize = 18.sp, fontWeight = FontWeight.Bold)

        if (filteredTrips.isEmpty()) {
            Box(modifier = Modifier.fillMaxWidth().padding(top = 16.dp), contentAlignment = Alignment.Center) {
                Text("No rides match your filters", color = Color.Gray)
            }
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                filteredTrips.forEach { trip ->
                    val dateParts = trip.date.split(" ")
                    TripCard(
                        ride = GebuchteRides(
                            id = trip.id.toString(),
                            month = dateParts.getOrElse(1) { "" }.uppercase(),
                            day = dateParts.getOrElse(0) { "" },
                            from = trip.fromCity,
                            to = trip.toCity,
                            time = "",
                            driver = "User",
                            status = TripStatus.CONFIRMED
                        ),
                        onClick = {
                            navController.navigate("rideDetails/${trip.id}/false")
                        }
                    )
                }
            }
        }
    }
}
