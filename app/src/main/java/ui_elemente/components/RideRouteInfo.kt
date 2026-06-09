package ui_elemente.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RideRouteInfo(
    startTime: String,
    startLocation: String,
    endTime: String,
    endLocation: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 22.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(28.dp)
        ) {
            Canvas(modifier = Modifier.size(18.dp)) {
                drawCircle(Color.DarkGray)
            }

            Canvas(
                modifier = Modifier
                    .width(2.dp)
                    .height(72.dp)
            ) {
                drawLine(
                    color = Color.DarkGray,
                    start = Offset(size.width / 2, 0f),
                    end = Offset(size.width / 2, size.height),
                    strokeWidth = 4f
                )
            }

            Canvas(modifier = Modifier.size(18.dp)) {
                drawCircle(
                    color = Color.DarkGray,
                    style = Stroke(width = 5f)
                )
            }
        }

        Spacer(modifier = Modifier.width(18.dp))

        Column(
            modifier = Modifier.width(80.dp)
        ) {
            Text(
                text = startTime,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(58.dp))

            Text(
                text = endTime,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = startLocation,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Pickup point",
                fontSize = 15.sp,
                color = Color.Gray,
                fontStyle = FontStyle.Italic
            )

            Spacer(modifier = Modifier.height(44.dp))

            Text(
                text = endLocation,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Drop-off point",
                fontSize = 15.sp,
                color = Color.Gray,
                fontStyle = FontStyle.Italic
            )
        }
    }
}