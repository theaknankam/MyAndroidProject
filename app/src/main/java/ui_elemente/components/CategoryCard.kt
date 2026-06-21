package ui_elemente.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.carsharing_app.R
@Composable
fun CategoryCard(
    modifier: Modifier,
    navController: NavHostController,
    category: Category

    ) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(140.dp),
        onClick = {
            navController.navigate(category.route)
        },
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(
                        Color(0xFF22464F),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(category.icon),
                    contentDescription = category.label,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = category.label,
                textAlign = TextAlign.Center
            )
        }
    }
}

enum class Category(
    val label: String,
    val icon: Int,
    val route: String
) {
    BOOK("Fahrt buchen", R.drawable.ic_car_white, "autoauswahl"),
    BOOKINGS("Gebuchte Fahrten", R.drawable.ic_car_white, "gebuchteRides"),
    OFFER("Auto anbieten", R.drawable.ic_car_white,"createRide"),
    ANNOUNCE("Fahrt ankündigen", R.drawable.ic_car_white,"")
}