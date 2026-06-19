package ui_elemente.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.CheckCircle
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
fun InfoRow(
    icon: ImageVector,
    title: String,
    value: String,
    showArrow: Boolean = false,
    showCheck: Boolean = false,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = Color.DarkGray,
            modifier = Modifier.size(26.dp)
        )

        Spacer(modifier = Modifier.width(20.dp))

        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(100.dp)
        )

        Text(
            text = value,
            fontSize = 16.sp,
            color = Color.DarkGray,
            modifier = Modifier.weight(1f)
        )

        if (showArrow) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Arrow",
                tint = Color.Black
            )
        }

        if (showCheck) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Verified",
                tint = Color.DarkGray,
                modifier = Modifier.size(22.dp)
            )
        }
    }
}