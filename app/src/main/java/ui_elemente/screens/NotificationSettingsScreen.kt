package ui_elemente.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ui_elemente.navigation.Topbar

@Composable
fun NotificationSettingsScreen(navController: NavHostController) {
    var pushEnabled by remember { mutableStateOf(true) }
    var emailEnabled by remember { mutableStateOf(false) }
    var smsEnabled by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            Topbar("Notifications", navController)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
                .padding(16.dp)
        ) {
            NotificationToggle(
                title = "Push Notifications",
                description = "Receive alerts on your device",
                checked = pushEnabled,
                onCheckedChange = { pushEnabled = it }
            )
            NotificationToggle(
                title = "Email Notifications",
                description = "Receive updates via email",
                checked = emailEnabled,
                onCheckedChange = { emailEnabled = it }
            )
            NotificationToggle(
                title = "SMS Notifications",
                description = "Receive important alerts via SMS",
                checked = smsEnabled,
                onCheckedChange = { smsEnabled = it }
            )
        }
    }
}

@Composable
fun NotificationToggle(
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Text(text = description, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        }
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
    HorizontalDivider()
}
