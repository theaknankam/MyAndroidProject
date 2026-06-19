package ui_elemente.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import ui_elemente.model.enums.VehiculeType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehiculeDropdown(
    selected: VehiculeType?,
    onSelected: (VehiculeType?) -> Unit,
    modifier: Modifier = Modifier
) {
    val options = VehiculeType.entries

    var expanded by remember {
        mutableStateOf(false)
    }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        },
        modifier = modifier
    ) {

        TextField(
            value = selected?.name ?: "All",
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            DropdownMenuItem(
                text = { Text("All") },
                onClick = {
                    onSelected(null)
                    expanded = false
                }
            )

            options.forEach { option ->

                DropdownMenuItem(
                    text = {
                        Text(option.name)
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