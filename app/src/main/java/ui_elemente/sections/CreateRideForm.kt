@file:OptIn(ExperimentalMaterial3Api::class)

package ui_elemente.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.carsharing_app.data.TripViewModel
import ui_elemente.components.DatePickerField
import ui_elemente.components.LocationInput
import ui_elemente.components.PriceField
import ui_elemente.components.PrimaryButton
import ui_elemente.components.SeatSelector
import ui_elemente.components.SimpleMapPreview

@Composable
fun CreateRideForm(
    navController: NavController,
    viewModel: TripViewModel = viewModel()
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        var location1 by remember { mutableStateOf("") }
        var location2 by remember { mutableStateOf("") }
        var date by remember { mutableStateOf("") }
        var seats by remember { mutableStateOf(1) }
        var price by remember { mutableStateOf(0) }

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

        SimpleMapPreview(
            fromLocation = location1,
            toLocation = location2
        )

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
                navController.navigate("suggestedRides")
            }
        )
    }
}
