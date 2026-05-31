package ui_elemente.components

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.type
import com.example.carsharing_app.ui.theme.Carsharing_appTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehiculeDropdown(
    selected: String,
    onSelected: (String) -> Unit
) {

    val options = listOf(
        "Scooter",
        "Cars",
        "Bike"
    )

    var expanded by remember {
        mutableStateOf(false)
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {

        TextField(
            value = selected,
            onValueChange = {},
            readOnly = true,
            label = {
                Text("Sortieren nach")
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            modifier = Modifier.menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {

            options.forEach { option ->

                DropdownMenuItem(
                    text = {
                        Text(option)
                    },
                    onClick = {
                        onSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}