package ui_elemente.navigation

import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.carsharing_app.R

@Composable
fun Backbutton(
    navController: NavHostController
) {
    Button(
        onClick = {
            navController.popBackStack()
        }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_back_black),
            contentDescription = "Back"
        )
    }

}
