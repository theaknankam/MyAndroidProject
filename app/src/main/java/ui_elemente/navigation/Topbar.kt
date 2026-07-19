package ui_elemente.navigation

import android.R.attr.onClick
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.carsharing_app.R
import ui_elemente.components.Greeting

@Composable
fun Topbar(lable: String,
           navController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val route = navBackStackEntry?.destination?.route
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 48.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (route != "home" && route != "login") {
            Backbutton(navController)

            Spacer(
                modifier = Modifier
                    .width(8.dp)
            )

        }

        Icon(
            painter = painterResource(id = R.drawable.ic_car_black),
            contentDescription = null,
            modifier = Modifier.size(24.dp)

        )

        Greeting(lable)

        Spacer(modifier = Modifier
            .weight(1f)
            )

        Icon(
            painter = painterResource(
                id = R.drawable.ic_setting_black,
            ),
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
                .clickable{ navController.navigate("settings") }

        )

    }
}