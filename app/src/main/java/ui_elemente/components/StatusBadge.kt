package ui_elemente.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui_elemente.model.enums.TripStatus
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color

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