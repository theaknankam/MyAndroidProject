package ui_elemente.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.carsharing_app.R
import ui_elemente.navigation.Topbar
import ui_elemente.viewModel.ReserveVieuwModel
import ui_elemente.viewModel.VehiculeViewmodel

@Composable
fun AuswahlBuchung(
    navController: NavHostController,
    modifier: Modifier
) {

    val reserveViewModel: ReserveVieuwModel = viewModel()

    val reservation = reserveViewModel.reservation


    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Topbar("Buchen", navController)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (reservation != null) {
                    Image(
                        painter = painterResource(id = reservation.vehicle.image),
                        contentDescription = "Logo",
                        modifier = Modifier.size(80.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))


                }
            }
        }
    }
}