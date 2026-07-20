package ui_elemente.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ui_elemente.navigation.Topbar

@Composable
fun AboutScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            Topbar("About", navController)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            
            Text(
                text = "CarSharing App",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            
            Text(
                text = "Version 1.0.0",
                fontSize = 16.sp,
                color = Color.Gray
            )
            
            Spacer(modifier = Modifier.height(48.dp))
            
            Text(
                text = "This application was designed to make carpooling easier and more efficient for everyone. Share rides, save costs, and help the environment.",
                fontSize = 16.sp,
                color = Color.DarkGray,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            
            Spacer(modifier = Modifier.weight(1f))
            
            Text(
                text = "© 2025 CarSharing Team" +
                        "Liya, Diana & Thea",
                fontSize = 14.sp,
                color = Color.LightGray
            )
        }
    }
}
