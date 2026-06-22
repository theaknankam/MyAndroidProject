package ui_elemente.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun PriceField(
    price: Int,
    onPriceChange: (Int) -> Unit
) {
    Column {

        Text(text = "Price per seat")

        OutlinedTextField(
            value = if (price == 0) "" else price.toString(),
            onValueChange = { input ->
                val digitsOnly = input.filter { it.isDigit() }
                onPriceChange(digitsOnly.toIntOrNull() ?: 0)
            },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.AttachMoney,
                    contentDescription = null
                )
            },
            placeholder = {
                Text("0")
            }
        )
    }
}