package ui_elemente.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ui_elemente.components.DriverInfo
import ui_elemente.components.RideFacts
import ui_elemente.components.RideRouteInfo
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.carsharing_app.Karte.MapScreen
import com.example.carsharing_app.Karte.geocode
import com.example.carsharing_app.data.TripViewModel
import org.osmdroid.util.GeoPoint
import ui_elemente.components.RideDetailsButton
import ui_elemente.navigation.Topbar

@Composable
fun RideDetailsScreen(
    tripId: String,
    navController: NavHostController,
    viewModel: TripViewModel = viewModel(),
    isBooked: Boolean = false,
) {
    val context = LocalContext.current
    val trips by viewModel.allTrips.collectAsState()
    val firestoreTrips = viewModel.firestoreTrips

    val trip = trips.find { it.id.toString() == tripId }
        ?: firestoreTrips.find { it.id.toString() == tripId }

    if (trip == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
        return
    }

    Scaffold(
        topBar = { Topbar("Ride Details", navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
                .verticalScroll(rememberScrollState())
        ) {
            DriverInfo(
                driverName = if (trip.createdBy == "me") "You" else "Driver",
                rating = 4.8,
                memberSince = "2024"
            )

            // Preference Icons (2)
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                PreferenceIcon(icon = Icons.Default.SmokingRooms, enabled = trip.allowSmoking, label = "Smoking")
                PreferenceIcon(icon = Icons.Default.Pets, enabled = trip.allowPets, label = "Pets")
                PreferenceIcon(icon = Icons.Default.MusicNote, enabled = trip.allowMusic, label = "Music")
                if (trip.ladiesOnly) PreferenceIcon(icon = Icons.Default.Female, enabled = true, label = "Ladies")
            }

            HorizontalDivider(color = Color(0xFFE0E0E0))

            RideRouteInfo(
                startTime = "08:30",
                startLocation = trip.fromCity,
                endTime = "11:45",
                endLocation = trip.toCity
            )

            HorizontalDivider(color = Color(0xFFE0E0E0))

            RideFacts(
                date = trip.date,
                seatsLeft = trip.seats,
                pricePerSeat = trip.price
            )

            Text(
                text = "Route",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 24.dp, bottom = 12.dp)
            )

            var pointA by remember { mutableStateOf<GeoPoint?>(null) }
            var pointB by remember { mutableStateOf<GeoPoint?>(null) }

            LaunchedEffect(trip) {
                pointA = geocode(trip.fromCity)
                pointB = geocode(trip.toCity)
            }

            Box(modifier = Modifier.padding(horizontal = 24.dp).height(200.dp)) {
                if (pointA != null && pointB != null) {
                    MapScreen(pointA = pointA!!, pointB = pointB!!)
                } else {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            RideDetailsButton(
                isBooked = isBooked,
                onClick = {
                    if (isBooked) {
                        navController.navigate("chat/${trip.createdBy}")
                    } else {
                        viewModel.bookTrip(trip)
                        Toast.makeText(context, "Payment of €${trip.price} via Wallet confirmed!", Toast.LENGTH_LONG).show()
                        navController.navigate("gebuchteRides")
                    }
                }
            )
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun PreferenceIcon(icon: androidx.compose.ui.graphics.vector.ImageVector, enabled: Boolean, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (enabled) MaterialTheme.colorScheme.primary else Color.LightGray,
            modifier = Modifier.size(28.dp)
        )
        Text(text = label, fontSize = 10.sp, color = if (enabled) Color.Black else Color.LightGray)
    }
}
