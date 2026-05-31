package ui_elemente.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.carsharing_app.R


@Composable
fun AutoCard(
    name: String,
    image: Int
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),

        shape = RoundedCornerShape(16.dp)
    ) {

        Column {

            Image(
                painter = painterResource(image),
                contentDescription = name,

                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clickable{},

                contentScale = ContentScale.Crop
            )

            Text(
                text = name,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
enum class AutoImages(
    val label: String,
    val icon: Int,
) {

    CAR1("Mazda", R.drawable.car1),

    CAR2("Range Rover", R.drawable.car2),

    CAR3("Twingo", R.drawable.car3),

    CAR4("Umzugswagen", R.drawable.car4),

    CAR5("Mazda", R.drawable.car1),

    CAR6("Range Rover", R.drawable.car2),

    CAR7("Twingo", R.drawable.car3),

    CAR8("Umzugswagen", R.drawable.car4),

    SCOOTER1("Scooter", R.drawable.scooter),

    SCOOTER2("Scooter", R.drawable.scooter)

}

