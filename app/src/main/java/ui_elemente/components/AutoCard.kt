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
import ui_elemente.model.Vehicule


@Composable
fun AutoCard(
    vehicule: Vehicule
//    price: String

) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),

        shape = RoundedCornerShape(16.dp)
    ) {

        Column {

            Image(
                painter = painterResource(vehicule.image),
                contentDescription = vehicule.brand.toString(),

                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clickable{},

                contentScale = ContentScale.Crop
            )

            Text(
                text = vehicule.brand.toString(),
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
