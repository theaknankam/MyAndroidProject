package ui_elemente.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui_elemente.model.enums.TripStatus
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import ui_elemente.model.GebuchteRides

@Composable
fun StatusBadge(status: TripStatus) {
    val (label, bgColor) = when (status) {
        TripStatus.CONFIRMED -> "Confirmed" to Color(0xFFE0E0E0)
        TripStatus.PENDING -> "Pending" to Color(0xFFEFEFEF)
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(bgColor)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(text = label, fontSize = 12.sp, color = Color.DarkGray)
    }
}

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