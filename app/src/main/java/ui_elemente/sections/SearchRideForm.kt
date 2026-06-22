package ui_elemente.sections


import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.carsharing_app.data.TripViewModel
import androidx.compose.ui.graphics.Color

import ui_elemente.components.DatePickerField
import ui_elemente.components.LocationInput
import ui_elemente.components.PriceField
import ui_elemente.components.PrimaryButton
import ui_elemente.components.RoutePreview
import ui_elemente.components.SeatSelector

@Composable
fun SearchRideForm(
    viewModel: TripViewModel = viewModel()
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        var location1 by remember { mutableStateOf("") }
        var location2 by remember { mutableStateOf("") }
        var date by remember { mutableStateOf("") }
        var seats by remember { mutableStateOf(1) }
        var price by remember { mutableStateOf(0) }

        val allTrips by viewModel.allTrips.collectAsState()

        LocationInput(
            label = "From",
            value = location1,
            onValueChange = { location1 = it }
        )

        LocationInput(
            label = "To",
            value = location2,
            onValueChange = { location2 = it }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            DatePickerField(
                modifier = Modifier.weight(1f),
                value = date,
                onValueChange = { date = it }
            )
        }

        SeatSelector(
            seats = seats,
            onSeatsChange = { seats = it }
        )

        PriceField(
            price = price,
            onPriceChange = { price = it }
        )

        Spacer(modifier = Modifier.width(8.dp))

        PrimaryButton(
            text = "Search",
            onClick = {}
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Bereich: Empfohlene Rides
        Text(
            text = "Suggested Rides",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            allTrips.forEach { trip ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(12.dp))
                        .padding(16.dp)
                ) {
                    Text(
                        text = "${trip.fromCity} → ${trip.toCity}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(text = "Date: ${trip.date}", color = Color.DarkGray, fontSize = 14.sp)
                    Text(
                        text = "Seats: ${trip.seats}  •  € ${trip.price}",
                        color = Color.DarkGray,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

