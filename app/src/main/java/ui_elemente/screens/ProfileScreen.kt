package ui_elemente.screens

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui_elemente.navigation.Topbar

@Composable
fun ProfileScreen() {
    Scaffold(
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
                .padding(horizontal = 24.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Topbar("Profile")

                // Obere Leiste mit Zurück-Pfeil und Settings
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }

                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = Color.Black
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Profilbild
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE0E0E0)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile Image",
                            tint = Color.DarkGray,
                            modifier = Modifier.size(70.dp)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .padding(start = 85.dp, top = 75.dp)
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .border(1.dp, Color.LightGray, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = "Camera",
                            tint = Color.Black,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Name
                Text(
                    text = "John Doe",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Bewertung
                Row(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = Color.Black,
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = "4.8 (32 reviews)",
                        fontSize = 15.sp,
                        color = Color.DarkGray
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                // Info-Karte
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp)
                    ) {
                        InfoRow(
                            icon = Icons.Default.DirectionsCar,
                            title = "Car",
                            value = "Toyota Corolla 2020",
                            showArrow = true
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        InfoRow(
                            icon = Icons.Default.Email,
                            title = "Verified",
                            value = "Email, Phone",
                            showCheck = true
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        InfoRow(
                            icon = Icons.Default.LocationOn,
                            title = "Lives in",
                            value = "Cologne, Germany",
                            showArrow = true
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Ride History Überschrift
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Ride History",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "See all",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.DarkGray
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Ride History Karte
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                ) {
                    Column {
                        RideHistoryItem(
                            date = "12 May 2024",
                            route = "Cologne → Berlin"
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(Color(0xFFE0E0E0))
                        )

                        RideHistoryItem(
                            date = "05 May 2024",
                            route = "Düsseldorf → Frankfurt"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun InfoRow(
    icon: ImageVector,
    title: String,
    value: String,
    showArrow: Boolean = false,
    showCheck: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = Color.DarkGray,
            modifier = Modifier.size(26.dp)
        )

        Spacer(modifier = Modifier.width(20.dp))

        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(100.dp)
        )

        Text(
            text = value,
            fontSize = 16.sp,
            color = Color.DarkGray,
            modifier = Modifier.weight(1f)
        )

        if (showArrow) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Arrow",
                tint = Color.Black
            )
        }

        if (showCheck) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Verified",
                tint = Color.DarkGray,
                modifier = Modifier.size(22.dp)
            )
        }
    }
}

@Composable
fun RideHistoryItem(
    date: String,
    route: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = date,
                fontSize = 14.sp,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = route,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = "Open ride",
            tint = Color.Black
        )
    }
}

