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
import ui_elemente.navigation.Topbar



@Composable
fun RideDetailsScreen(
    tripId: String,
    navController: NavHostController
) {
    val context = LocalContext.current

    val ride = Ride(
        driverName = "John Doe",
        rating = 4.7,
        memberSince = "2022",
        startTime = "14:00",
        from = "Cologne, Germany",
        endTime = "18:30",
        to = "Berlin, Germany",
        date = "12 May 2024",
        seatsLeft = 3,
        pricePerSeat = 20

    )


    Scaffold { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
                .verticalScroll(rememberScrollState() )

        ) {
            Topbar("RideDetails", navController)





            DriverInfo(
                driverName = ride.driverName,
                rating = ride.rating,
                memberSince = ride.memberSince
            )

            Divider(color = Color(0xFFE0E0E0))

            RideRouteInfo(
                startTime = ride.startTime,
                startLocation = ride.from,
                endTime = ride.endTime,
                endLocation = ride.to
            )

            Divider(color = Color(0xFFE0E0E0))

            RideFacts(
                date = ride.date,
                seatsLeft = ride.seatsLeft,
                pricePerSeat = ride.pricePerSeat
            )

            Text(
                text = "Route",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 24.dp, bottom = 12.dp)
            )

            Box(
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {
                RouteMapPreview()
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    Toast
                        .makeText(context, "Seat booked", Toast.LENGTH_SHORT)
                        .show()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 24.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.DarkGray,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Book Seat",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}