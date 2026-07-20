@file:OptIn(ExperimentalMaterial3Api::class)

package ui_elemente.sections

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import ui_elemente.navigation.Topbar

@Composable
fun CreateRideForm(
    navController: NavController,
    viewModel: TripViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    
    var location1 by remember { mutableStateOf("") }
    var location2 by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var seats by remember { mutableStateOf(1) }
    var price by remember { mutableStateOf(0) }
    
    // Preferences
    var allowSmoking by remember { mutableStateOf(false) }
    var allowPets by remember { mutableStateOf(false) }
    var allowMusic by remember { mutableStateOf(true) }
    var ladiesOnly by remember { mutableStateOf(false) }

    var pointA by remember { mutableStateOf<GeoPoint?>(null) }
    var pointB by remember { mutableStateOf<GeoPoint?>(null) }
    var isLoadingMap by remember { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Topbar("Create Ride", navController as NavHostController)

        LocationInput(label = "From", value = location1, onValueChange = { 
            location1 = it
            if (it.length >= 3) {
                coroutineScope.launch {
                    isLoadingMap = true
                    pointA = geocode(it)
                    isLoadingMap = false
                }
            }
        })

        LocationInput(label = "To", value = location2, onValueChange = { 
            location2 = it
            if (it.length >= 3) {
                coroutineScope.launch {
                    isLoadingMap = true
                    pointB = geocode(it)
                    isLoadingMap = false
                }
            }
        })

        DatePickerField(modifier = Modifier.fillMaxWidth(), value = date, onValueChange = { date = it })
        
        SeatSelector(seats = seats, onSeatsChange = { seats = it })
        
        PriceField(price = price, onPriceChange = { price = it })

        // Preferences Section (2)
        Text("Preferences", style = MaterialTheme.typography.titleMedium)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            PreferenceToggle(label = "Smoking", checked = allowSmoking, onCheckedChange = { allowSmoking = it })
            PreferenceToggle(label = "Pets", checked = allowPets, onCheckedChange = { allowPets = it })
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            PreferenceToggle(label = "Music", checked = allowMusic, onCheckedChange = { allowMusic = it })
            PreferenceToggle(label = "Ladies Only", checked = ladiesOnly, onCheckedChange = { ladiesOnly = it })
        }

        if (isLoadingMap) {
            Box(modifier = Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
        } else if (pointA != null && pointB != null) {
            MapScreen(pointA = pointA!!, pointB = pointB!!)
        }

        PrimaryButton(
            text = "Publish Ride",
            onClick = {
                viewModel.addTrip(
                    fromCity = location1,
                    toCity = location2,
                    date = date,
                    seats = seats,
                    price = price,
                    allowSmoking = allowSmoking,
                    allowPets = allowPets,
                    allowMusic = allowMusic,
                    ladiesOnly = ladiesOnly
                )
                navController.navigate("searchRide")
            }
        )
    }
}

@Composable
fun PreferenceToggle(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = checked, onCheckedChange = onCheckedChange)
        Text(label, style = MaterialTheme.typography.bodyMedium)
    }
}
