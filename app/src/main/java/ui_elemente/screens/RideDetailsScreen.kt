package ui_elemente.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import ui_elemente.components.RouteMapPreview
import ui_elemente.model.Ride
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

    // ← erst in Room suchen, dann in Firestore
    val trip = trips.find { it.id.toString() == tripId }
        ?: firestoreTrips.find { it.id.toString() == tripId }

    if (trip == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
                .verticalScroll(rememberScrollState())
        ) {
            DriverInfo(
                driverName = "You",
                rating = 5.0,
                memberSince = "2024"
            )

            Divider(color = Color(0xFFE0E0E0))

            RideRouteInfo(
                startTime = "",
                startLocation = trip.fromCity,
                endTime = "",
                endLocation = trip.toCity
            )

            Divider(color = Color(0xFFE0E0E0))

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

            // Karte mit echten Städten
            var pointA by remember { mutableStateOf<GeoPoint?>(null) }
            var pointB by remember { mutableStateOf<GeoPoint?>(null) }

            LaunchedEffect(trip) {
                pointA = geocode(trip.fromCity)
                pointB = geocode(trip.toCity)
            }

            Box(modifier = Modifier.padding(horizontal = 24.dp)) {
                if (pointA != null && pointB != null) {
                    MapScreen(
                        pointA = pointA!!,
                        pointB = pointB!!
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            RideDetailsButton(
                isBooked = isBooked,
                onClick = {
                    if (isBooked) {
                        navController.navigate("chat/${trip.createdBy}")
                    } else {
                        viewModel.bookTrip(trip)
                        Toast.makeText(context, "Seat booked!", Toast.LENGTH_SHORT).show()
                        navController.navigate("gebuchteRides")
                    }
                }
            )

        }
    }
}