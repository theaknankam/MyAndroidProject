package ui_elemente.navigation

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.carsharing_app.R

@Composable
fun Backbutton(
    navController: NavHostController
) {
    Button(
        modifier = Modifier
//            .padding(16.dp)
            .width(80.dp)
            .height(40.dp),
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
