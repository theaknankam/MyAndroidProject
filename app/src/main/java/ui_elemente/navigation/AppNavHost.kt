package ui_elemente.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ui_elemente.screens.Autoauswahl
import ui_elemente.screens.ChatListScreen
import ui_elemente.screens.ChatScreen
import ui_elemente.screens.CreateRideScreen
import ui_elemente.screens.GebuchteRidesScreen
import ui_elemente.screens.HomeScreen
import ui_elemente.screens.LoginScreen
import ui_elemente.screens.ProfileScreen
import ui_elemente.screens.RideDetailsScreen
import ui_elemente.screens.SearchRideScreen
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
            HomeScreen(navController)
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
            ProfileScreen(navController)
        }


        composable(
            route = "rideDetails/{tripId}/{isBooked}",
            arguments = listOf(
                navArgument("tripId") { type = NavType.StringType },
                navArgument("isBooked") { type = NavType.BoolType }
            )
        )
            { backStackEntry ->
            val tripId = backStackEntry.arguments?.getString("tripId") ?: ""
            val isBooked = backStackEntry.arguments?.getBoolean("isBooked") ?: false
                RideDetailsScreen(
                tripId = tripId,
                    isBooked = isBooked,
                navController= navController
            )
        }

        composable("searchRide") {
            SearchRideScreen(navController)
        }


        composable("chat") {
            ChatListScreen(navController = navController)  // ← war ChatScreen
        }

        composable(
            route = "chat/{driverId}",
            arguments = listOf(
                navArgument("driverId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val driverId = backStackEntry.arguments?.getString("driverId") ?: ""
            ChatScreen(
                navController = navController,
                driverId = driverId
            )
        }

        composable("gebuchteRides") {
            GebuchteRidesScreen(
                viewModel = viewModel(),
                tripViewModel = viewModel(),// gibt jetzt TripViewModel zurück
                navController = navController
            )
        }

    }
}