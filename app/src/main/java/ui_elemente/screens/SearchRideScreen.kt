package ui_elemente.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ui_elemente.navigation.Topbar
import ui_elemente.sections.CreateRideForm
import ui_elemente.sections.SearchRideForm

@Composable
fun SearchRideScreen(
    navController: NavHostController

) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .background(Color.White)
        /*verticalArrangement = Arrangement.spacedBy(10.dp)*/
    ) {

        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = "Search Rides",
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium
            )
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription= "Settings"
            )
        }
        //Spacer(modifier = Modifier.height(3.dp))

       SearchRideForm()

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = "Available Rides",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Left,
            style = MaterialTheme.typography.titleSmall
        )

    }
}
