package ui_elemente.screensView

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ui_elemente.screensView.sections.CreateRideForm

@Composable
fun CreateRideScreen(
    navController : NavHostController
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding( 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        //Topbar("Create Ride", navController)

       // Spacer(modifier = Modifier.height(16.dp))

        CreateRideForm(
            navController = navController
        )


    }
}