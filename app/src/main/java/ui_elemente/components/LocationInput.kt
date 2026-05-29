package ui_elemente.components

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.Modifier


@Composable
fun LocationInput(
    label: String,
    value:String,
    onValueChange: (String) -> Unit
) {

        OutlinedTextField( /* Das ist das eigentliche Eingabefeld. */
            value = value, /*der eingegebene text wird gezeigt*/
            onValueChange = onValueChange,
            label = {
                Text(label)  /*Das definiert den kleinen Titel im Textfeld.*/
            },
                    modifier = Modifier.fillMaxWidth()
        )
    }
