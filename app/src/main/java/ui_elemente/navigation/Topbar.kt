package ui_elemente.navigation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.carsharing_app.R
import ui_elemente.components.Greeting

@Composable
fun Topbar(lable: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 48.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_car_black),
            contentDescription = null,
            modifier = Modifier.size(24.dp)

        )
        Greeting(lable)
        Spacer(modifier = Modifier
            .padding(horizontal = 150.dp
            ))
        Icon(
            painter = painterResource(id = R.drawable.ic_setting_black),
            contentDescription = null,
            modifier = Modifier.size(24.dp)

        )
    }
}