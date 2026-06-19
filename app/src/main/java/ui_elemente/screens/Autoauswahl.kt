package ui_elemente.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ui_elemente.components.AutoCard
import ui_elemente.components.SearchBar
import ui_elemente.components.VehiculeDropdown
import ui_elemente.model.Vehicule
import ui_elemente.model.enums.VehiculeType
import ui_elemente.navigation.Topbar
import ui_elemente.viewModel.VehiculeViewmodel

@Composable
fun Autoauswahl(
    viewModel: VehiculeViewmodel ,
    navController : NavController


) {

    var selectedType  by remember {
        mutableStateOf<VehiculeType?>(null)
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
                    selected = selectedType ,
                    onSelected = { selectedType  = it },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                SearchBar(
                    modifier = Modifier.weight(1f)
                )
            }

            val vehicules by viewModel.vehicles
                .observeAsState(emptyList())

            val filteredCars =
                if (selectedType == null)
                    vehicules
                else
                    vehicules.filter {
                        it.type == selectedType
                    }
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 8.dp)
                    .clickable {}
            ) {

                items(filteredCars) { car ->
                    AutoCard(car)
                }
            }
        }

    }
}

