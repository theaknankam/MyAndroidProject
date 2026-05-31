package ui_elemente.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ui_elemente.components.AutoCard
import ui_elemente.components.AutoImages
import ui_elemente.components.Greeting
import ui_elemente.components.SearchBar
import ui_elemente.components.VehiculeDropdown


@Preview(showBackground = true)
@Composable
fun HomeScreen() {

    var sortOption by remember {
        mutableStateOf("Vehicule")
    }

    Column {
        Greeting("Carsharing")

        VehiculeDropdown(
            selected = sortOption,
            onSelected = {
                sortOption = it
            }
        )
        SearchBar()
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
                .clickable{}
        ) {

            val sortedCars =
                if(sortOption == "Name")
                    AutoImages.entries.sortedBy { it.label }
                else
                    AutoImages.entries

            items(sortedCars) { car ->
                AutoCard(
                    name = car.label,
                    image = car.icon
                )
            }
        }
    }
}