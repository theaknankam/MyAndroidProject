package ui_elemente.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import android.R.attr.label
import android.R.attr.value
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
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
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null
                )},
              modifier = Modifier.fillMaxWidth()
                )
    }
