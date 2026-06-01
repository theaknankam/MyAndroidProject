package ui_elemente.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.carsharing_app.R


@Composable
fun SearchBar(modifier: Modifier) {

    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable {
//                onClick()
            },
        shape = RoundedCornerShape(16.dp)
    ) {

        Row(
            modifier = Modifier.padding(16.dp)
        ) {

            Icon(
                painter = painterResource(R.drawable.ic_searchloop),
                contentDescription = "Search"
            )

            Text(
                text = "Search",
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}