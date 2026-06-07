package com.example.carsharing_app.Navigation

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import com.example.carsharing_app.R
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ui_elemente.screens.Autoauswahl
import ui_elemente.screens.CreateRideScreen
import ui_elemente.screens.HomeScreen


@Composable
fun Navigation() {

    var currentDestination by rememberSaveable {
        mutableStateOf(AppDestinations.HOME)
    }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.forEach {
                item(
                    icon = {
                        Icon(
                            painter = painterResource(it.icon),
                            contentDescription = it.label,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = {
                        Text(it.label)
                    },
                    selected = it == currentDestination,
                    onClick = {
                        currentDestination = it
                    }
                )
            }
        }
    ) {
        when(currentDestination) {
            AppDestinations.HOME -> HomeScreen()
            AppDestinations.RIDE -> Autoauswahl()
            AppDestinations.PROFILE -> Autoauswahl() // Placeholder
            AppDestinations.MESSAGE -> HomeScreen() // Placeholder
        }
    }
}

enum class AppDestinations(
    val label: String,
    val icon: Int,
) {
    HOME("Home", R.drawable.ic_home),
    RIDE("Ride", R.drawable.ic_car_white),
    MESSAGE("Message", R.drawable.ic_message_white),
    PROFILE("Profile", R.drawable.ic_account_box)
}
