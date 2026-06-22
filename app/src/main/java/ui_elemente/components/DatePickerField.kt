package ui_elemente.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import java.util.Date


@Composable
fun DatePickerField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit
) {
    var showDialog by remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        value = value.ifEmpty { "Select date" },
        onValueChange = {},
        readOnly = true,
        label = { Text("Date") },
        modifier = modifier
            .fillMaxWidth()
            .clickable { showDialog = true }
    )
    if (showDialog) {
        val datePickerState = rememberDatePickerState()

        DatePickerDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    val millis = datePickerState.selectedDateMillis
                    val newDate =
                        millis?.let {
                            java.text.DateFormat
                                .getDateInstance()
                                .format(Date(it))
                        } ?: "No date"

                    onValueChange(newDate)
                    showDialog = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}