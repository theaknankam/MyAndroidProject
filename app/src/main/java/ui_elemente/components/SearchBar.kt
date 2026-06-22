package ui_elemente.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.carsharing_app.R


@Composable
fun SearchBar(modifier: Modifier,
              navController: NavHostController
) {

    Card(
        modifier
            .padding(8.dp)
            .width(70.dp)
            .height(60.dp)
            .clickable {
                navController.navigate("createRide")
                            },
        shape = RoundedCornerShape(16.dp)
    ) {

        Row {

            Icon(
                painter = painterResource(R.drawable.ic_searchloop),
                contentDescription = "Search"
            )

            Text(
                text = "Search",

            )
        }
    }
}