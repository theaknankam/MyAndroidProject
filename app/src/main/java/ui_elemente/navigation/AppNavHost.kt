package ui_elemente.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ui_elemente.screens.Autoauswahl
import ui_elemente.screens.CreateRideScreen
import ui_elemente.screens.HomeScreen
import ui_elemente.screens.LoginScreen
import ui_elemente.screens.ProfileScreen
import ui_elemente.screens.RideDetailsScreen
import ui_elemente.viewModel.VehiculeViewmodel

@Composable
fun AppNavHost(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        composable("login") {

            LoginScreen(

                onLoginSuccess = {

                    navController.navigate("home") {

                        popUpTo("login") {
                            inclusive = true
                        }

                        launchSingleTop = true
                    }
                }
            )
        }

        composable("home") {
            HomeScreen()
        }

        composable("createRide") {
            CreateRideScreen(navController)
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