package ui_elemente.navigation

import com.example.carsharing_app.R

enum class AppDestinations(
    val route: String,
    val label: String,
    val icon: Int
) {
    HOME(
        route = "home",
        label = "Home",
        icon = R.drawable.ic_home
    ),

    RIDE(
        route = "searchRide",
        label = "Ride",
        icon = R.drawable.ic_car_white
    ),

    CREATERIDE(
        route = "createRide",
        label = "Create Ride",
        icon = R.drawable.ic_plus
    ),

    MESSAGE(
        route = "createRide",
        label = "Message",
        icon = R.drawable.ic_message_white
    ),

    PROFILE(
        route = "profile",
        label = "Profile",
        icon = R.drawable.ic_account_box
    )
}