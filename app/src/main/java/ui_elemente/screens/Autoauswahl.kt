package ui_elemente.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui_elemente.Navigation.Topbar
import ui_elemente.components.AutoCard
import ui_elemente.components.AutoImages
import ui_elemente.components.Greeting
import ui_elemente.components.SearchBar
import ui_elemente.components.VehiculeDropdown

@Composable
fun Autoauswahl() {

    var sortOption by remember {
        mutableStateOf("Vehicule")
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Topbar("Carsharing")

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                VehiculeDropdown(
                    selected = sortOption,
                    onSelected = { sortOption = it },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                SearchBar(
                    modifier = Modifier.weight(1f)
                )
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 8.dp)
                    .clickable {}
            ) {
                val sortedCars = if (sortOption == "Name")
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
}
