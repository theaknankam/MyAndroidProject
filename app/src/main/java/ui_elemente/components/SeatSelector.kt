package ui_elemente.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun SeatSelector() {
    // Speichert den Zustand der Anzahl
    var seats by remember { mutableStateOf(1) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))

    ) {
        Row (modifier= Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween){

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(12.dp))
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(text="Seats available", fontSize = 12.sp)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                // Anzahl verringern (Wert kann nicht kleiner als 1 werden)
                IconButton(onClick = { if (seats > 1) seats-- }) {
                    Icon(Icons.Default.Remove, null)
                }

                Text(
                    text = seats.toString(), fontSize = 12.sp
                )
                // Anzahl erhöhen
                IconButton(onClick = { seats++ }) {
                    Icon(Icons.Default.Add, null)
                }
            }
        }
    }
}