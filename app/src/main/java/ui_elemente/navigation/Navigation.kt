package ui_elemente.navigation
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

import kotlinx.coroutines.delay

import com.example.carsharing_app.R

import ui_elemente.screens.Autoauswahl
import ui_elemente.screens.CreateRideScreen
import ui_elemente.screens.HomeScreen
import ui_elemente.screens.LoadingScreen
import ui_elemente.screens.ProfileScreen
import ui_elemente.screens.RideDetailsScreen

import ui_elemente.viewModel.VehiculeViewmodel
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun Navigation() {

    // Contrôleur de navigation
    val navController = rememberNavController()

    // Permet de savoir sur quelle page on est actuellement
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Etat du Loading Screen
    var isLoading by rememberSaveable {
        mutableStateOf(true)
    }

    LaunchedEffect(Unit) {
        delay(2000.milliseconds)
        isLoading = false
    }

    if (isLoading) {

        LoadingScreen()

    } else {

        NavigationSuiteScaffold(

            navigationSuiteItems = {

                AppDestinations.entries.forEach { destination ->

                    item(

                        // Icône de l'onglet
                        icon = {
                            Icon(
                                painter = painterResource(destination.icon),
                                contentDescription = destination.label,
                                modifier = Modifier.size(24.dp)
                            )
                        },

                        // Texte de l'onglet
                        label = {
                            Text(destination.label)
                        },

                        // Indique quel onglet est sélectionné
                        selected =
                            currentRoute ==
                                    when (destination) {
                                        AppDestinations.HOME -> "home"
                                        AppDestinations.MESSAGE -> "createRide"
                                        AppDestinations.RIDE -> "autoauswahl"
                                        AppDestinations.PROFILE -> "profile"
                                    },

                        // Que faire lorsqu'on clique sur un onglet
                        onClick = {

                            when (destination) {

                                AppDestinations.HOME ->
                                    navController.navigate("home") {
                                        launchSingleTop = true
                                    }

                                AppDestinations.MESSAGE ->
                                    navController.navigate("createRide") {
                                        launchSingleTop = true
                                    }

                                AppDestinations.RIDE ->
                                    navController.navigate("autoauswahl") {
                                        launchSingleTop = true
                                    }

                                AppDestinations.PROFILE ->
                                    navController.navigate("profile") {
                                        launchSingleTop = true
                                    }
                            }
                        }
                    )
                }
            }

        ) {

            // Ici on déclare tous les écrans de l'application
            NavHost(
                navController = navController,
                startDestination = "home"
            ) {

                composable("home") {
                    HomeScreen()
                }

                composable("createRide") {
                    CreateRideScreen(
                        navController = navController)
                }

                composable("autoauswahl") {

                    val viewModel: VehiculeViewmodel = viewModel()

                    Autoauswahl(
                        viewModel = viewModel,
                        navController = navController
                    )
                }

                composable("profile") {
                    ProfileScreen()
                }

                composable("rideDetails") {
                    RideDetailsScreen()
                }
            }
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
