@file:OptIn(ExperimentalMaterial3Api::class)

package ui_elemente.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.carsharing_app.Karte.MapScreen
import com.example.carsharing_app.Karte.geocode
import com.example.carsharing_app.data.TripViewModel
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint
import ui_elemente.components.DatePickerField
import ui_elemente.components.LocationInput
import ui_elemente.components.PriceField
import ui_elemente.components.PrimaryButton
import ui_elemente.components.SeatSelector
import ui_elemente.components.SimpleMapPreview
import ui_elemente.navigation.Topbar

@Composable
fun CreateRideForm(
    navController: NavController,
    viewModel: TripViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Topbar("Create Ride", navController as NavHostController)
        var location1 by remember { mutableStateOf("") }
        var location2 by remember { mutableStateOf("") }
        var date by remember { mutableStateOf("") }
        var seats by remember { mutableStateOf(1) }
        var price by remember { mutableStateOf(0) }


        var pointA by remember { mutableStateOf<GeoPoint?>(null) }
        var pointB by remember { mutableStateOf<GeoPoint?>(null) }
        var isLoadingMap by remember { mutableStateOf(false) }

        LocationInput(
            label = "From",
            value = location1,
            onValueChange = { location1 = it
                pointA = null
                if (it.length >= 3) {
                    coroutineScope.launch {
                        isLoadingMap = true
                        val result = geocode(it)
                        println("GEOCODE FROM '$it' → $result")
                        pointA = result
                        isLoadingMap = false  // ← immer resetten, egal ob null oder nicht
                    }
                } else {
                    isLoadingMap = false  // ← auch resetten wenn text zu kurz
                }
            }
        )

        LocationInput(
            label = "To",
            value = location2,
            onValueChange = { location2 = it
                pointB = null
                if (it.length >= 3) {
                    coroutineScope.launch {
                        isLoadingMap = true
                        val result = geocode(it)
                        println("GEOCODE TO '$it' → $result")
                        pointB = result
                        isLoadingMap = false  // ← immer resetten
                    }
                } else {
                    isLoadingMap = false  // ← auch resetten wenn text zu kurz
                }
            }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
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

        when {
            isLoadingMap -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            pointA != null && pointB != null -> {
                MapScreen(
                    pointA = pointA!!,
                    pointB = pointB!!
                )
            }
            location1.length >= 3 && location2.length >= 3 -> {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Städte konnten nicht gefunden werden")
                }
            }
        }


        PrimaryButton(
            text = "Publish Ride",
            onClick = {
                viewModel.addTrip(
                    fromCity = location1,
                    toCity = location2,
                    date = date,
                    seats = seats,
                    price = price
                )
                navController.navigate("searchRide")
            }
        )
    }
}
