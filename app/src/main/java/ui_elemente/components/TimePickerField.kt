package ui_elemente.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerField(onConfirm: () -> Unit, modifier: Modifier= Modifier) {
    var showDialog by remember {
        mutableStateOf(false)
    }
    var selectedTime by remember {
        mutableStateOf("14:00 ")
    }
    val currentTime = Calendar.getInstance()
    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )

    Column (modifier= modifier){
        OutlinedTextField(
            value = selectedTime,
            onValueChange = {},
            readOnly = true,
            label = { Text("Time") },
            modifier = Modifier.fillMaxWidth()
                .clickable { showDialog = !showDialog }
        )

        if (showDialog) {
            Spacer(modifier = Modifier.height(16.dp))

            TimePicker(
                state = timePickerState
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(onClick = { showDialog = false }) {
                    Text("Cancel")
                }


                Button(onClick = {
                    selectedTime =
                        "%02d:%02d".format(
                            timePickerState.hour,
                            timePickerState.minute
                        )

                    onConfirm()
                    showDialog = false
                }) {
                    Text("Confirm")
                }

            }
        }
    }
}