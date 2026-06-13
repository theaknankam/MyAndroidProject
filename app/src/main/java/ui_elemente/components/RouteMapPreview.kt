package ui_elemente.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun RouteMapPreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(170.dp)
            .background(
                color = Color(0xFFE5E5E5),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(22.dp)
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val path = Path().apply {
                moveTo(40f, size.height * 0.45f)
                cubicTo(
                    size.width * 0.25f,
                    size.height * 0.45f,
                    size.width * 0.30f,
                    size.height * 0.95f,
                    size.width * 0.50f,
                    size.height * 0.70f
                )
                cubicTo(
                    size.width * 0.70f,
                    size.height * 0.45f,
                    size.width * 0.70f,
                    size.height * 0.20f,
                    size.width - 40f,
                    size.height * 0.35f
                )
            }

            drawPath(
                path = path,
                color = Color.DarkGray,
                style = Stroke(width = 8f)
            )
        }

        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = "Start",
            tint = Color.DarkGray,
            modifier = Modifier
                .size(46.dp)
                .align(Alignment.CenterStart)
        )

        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = "End",
            tint = Color.DarkGray,
            modifier = Modifier
                .size(46.dp)
                .align(Alignment.CenterEnd)
        )
    }
}