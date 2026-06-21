package ui_elemente.navigation

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds
import ui_elemente.screens.LoadingScreen

@Composable
fun Navigation(

) {
    val navController: NavHostController = rememberNavController()


    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentRoute = navBackStackEntry?.destination?.route


    // Login = pas de barre de navigation
    if (currentRoute == "login" || currentRoute == null) {

        AppNavHost(navController,)

    } else {

        NavigationSuiteScaffold(

            navigationSuiteItems = {

                AppDestinations.entries.forEach { destination ->

                    item(

                        icon = {
                            Icon(
                                painter = painterResource(destination.icon),
                                contentDescription = destination.label,
                                modifier = Modifier.size(24.dp)
                            )
                        },

                        label = {
                            Text(destination.label)
                        },

                        selected = currentRoute == destination.route,

                        onClick = {

                            navController.navigate(destination.route) {

                                launchSingleTop = true

                                restoreState = true

                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                            }
                        }
                    )
                }
            }

        ) {

            AppNavHost(navController)

        }
    }
}