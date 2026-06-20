package ui_elemente.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui_elemente.model.GebuchteRides
import androidx.compose.foundation.clickable

@Composable
fun TripCard(ride: GebuchteRides, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFF5F5F5))
                .padding(horizontal = 14.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = ride.month, fontSize = 12.sp, color = Color.Gray)
            Text(text = ride.day, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "${ride.from} → ${ride.to}",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(text = ride.time, color = Color.DarkGray, fontSize = 14.sp)
            Text(text = "Driver: ${ride.driver}", color = Color.DarkGray, fontSize = 14.sp)
        }

        StatusBadge(status = ride.status)
    }
}
