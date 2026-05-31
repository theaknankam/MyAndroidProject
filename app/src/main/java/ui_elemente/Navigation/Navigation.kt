package com.example.carsharing_app.Navigation

import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import ui_elemente.screens.Autoauswahl


@Preview(showBackground = true)
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
                            contentDescription = it.label
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

        Autoauswahl()

    }

//    when(currentDestination) {
//
//        AppDestinations.HOME -> HomeScreen()
//
//        AppDestinations.FAVORITES -> FavoriteScreen()
//
//        AppDestinations.PROFILE -> ProfileScreen()
//    }
}

enum class AppDestinations(
    val label: String,
    val icon: Int,
) {
    HOME("Home", R.drawable.ic_home),
    FAVORITES("Favorites", R.drawable.ic_favorite),
    PROFILE("Profile", R.drawable.ic_account_box),
}

