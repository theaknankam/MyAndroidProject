package ui_elemente.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add

@Composable
fun SeatSelector() {
    // Speichert den Zustand der Anzahl
    var seats by remember { mutableStateOf(3) }

    Column {
        Text(text = "Seats available")

        Row(verticalAlignment = Alignment.CenterVertically) {
            // Anzahl verringern (Wert kann nicht kleiner als 1 werden)
            IconButton(onClick = { if (seats > 1) seats-- }) {
                Icon(Icons.Default.Remove, null)
            }

            Text(
                text = seats.toString(), modifier = Modifier.padding(horizontal = 16.dp)
            )
            // Anzahl erhöhen
            IconButton(onClick = { seats++ }) {
                Icon(Icons.Default.Add, null)
            }
        }
    }
}