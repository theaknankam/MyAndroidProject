package ui_elemente.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.EventSeat
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RideFacts(
    date: String,
    seatsLeft: Int,
    pricePerSeat: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 22.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            RideFactItem(
                icon = Icons.Default.CalendarToday,
                title = "Date",
                value = date,
                modifier = Modifier.weight(1f)
            )

            RideFactItem(
                icon = Icons.Default.EventSeat,
                title = "Seats",
                value = "$seatsLeft seats left",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(22.dp))

        RideFactItem(
            icon = Icons.Default.LocalOffer,
            title = "Price per seat",
            value = "€ $pricePerSeat",
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun RideFactItem(
    icon: ImageVector,
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = Color.DarkGray,
            modifier = Modifier.size(28.dp)
        )

        Spacer(modifier = Modifier.width(14.dp))

        Column {
            Text(
                text = title,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = value,
                fontSize = 15.sp,
                color = Color.DarkGray
            )
        }
    }
}