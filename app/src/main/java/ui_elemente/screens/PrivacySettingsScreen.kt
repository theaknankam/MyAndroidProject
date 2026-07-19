package ui_elemente.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ui_elemente.navigation.Topbar

@Composable
fun PrivacySettingsScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            Topbar("Privacy & Security", navController)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
                .padding(16.dp)
        ) {
            Text(
                text = "Privacy",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            PrivacyItem(title = "Location Access", description = "Control how we use your location")
            PrivacyItem(title = "Data Sharing", description = "Manage sharing with partners")
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "Security",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            PrivacyItem(title = "Change Password", description = "Update your account password")
            PrivacyItem(title = "Two-Factor Authentication", description = "Add extra security to your account")
        }
    }
}

@Composable
fun PrivacyItem(title: String, description: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        Text(text = title, style = MaterialTheme.typography.titleMedium)
        Text(text = description, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider()
    }
}
