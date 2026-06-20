package ui_elemente.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


import ui_elemente.components.DatePickerField
import ui_elemente.components.LocationInput
import ui_elemente.components.PriceField
import ui_elemente.components.PrimaryButton
import ui_elemente.components.RoutePreview
import ui_elemente.components.SeatSelector

@Composable
fun SearchRideForm() {

    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        var location1 by remember { mutableStateOf("") }
        var location2 by remember { mutableStateOf("") }
        LocationInput(
            label = "From",
            value = location1,
            onValueChange = {location1= it}
        )

        LocationInput(
            label = "To",
            value = location2,
            onValueChange = {location2= it}
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            DatePickerField(
                modifier = Modifier.weight(1f)
            )

            /*TimePickerField(
                 modifier = Modifier.weight(1f),
                 onConfirm = {}
             )*/
        }

        //SeatSelector()

        //PriceField()

        //RoutePreview()
        Spacer(modifier = Modifier.width(8.dp))
        PrimaryButton(
            text = "Search",
            onClick = {}
        )


    }
}
