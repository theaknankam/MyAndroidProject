package ui_elemente.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ui_elemente.navigation.Topbar
import ui_elemente.model.UserSession

@Composable
fun SettingsScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            Topbar("Settings", navController)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
        ) {
            SettingsItem(
                icon = Icons.Default.Notifications,
                title = "Notifications",
                subtitle = "Manage your app alerts",
                onClick = { navController.navigate("notificationSettings") }
            )
            SettingsItem(
                icon = Icons.Default.Security,
                title = "Privacy & Security",
                subtitle = "Control your data",
                onClick = { navController.navigate("privacySettings") }
            )
            SettingsItem(
                icon = Icons.Default.Info,
                title = "About",
                subtitle = "App version and terms",
                onClick = { navController.navigate("about") }
            )
            
            Spacer(modifier = Modifier.weight(1f))
            
            SettingsItem(
                icon = Icons.Default.Logout,
                title = "Delete Account",
                subtitle = "Exit your current session",
                textColor = MaterialTheme.colorScheme.error,
                onClick = {
                    UserSession.currentUser = null
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun SettingsItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (textColor == MaterialTheme.colorScheme.onSurface) MaterialTheme.colorScheme.primary else textColor,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = textColor
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
}
