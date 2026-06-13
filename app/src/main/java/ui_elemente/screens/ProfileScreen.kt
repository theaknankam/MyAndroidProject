package ui_elemente.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui_elemente.Navigation.Topbar
import ui_elemente.components.InfoRow
import ui_elemente.components.RideHistoryItem

@Composable
fun ProfileScreen() {

    val context = LocalContext.current

    Scaffold { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
                .padding(horizontal = 24.dp)
        ) {

            Topbar("Profile")

            Spacer(modifier = Modifier.height(10.dp))

            // Profilbild klickbar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        Toast
                            .makeText(context, "Profilbild bearbeiten", Toast.LENGTH_SHORT)
                            .show()
                    },
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

            // Name klickbar
            Text(
                text = "John Doe",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable {
                        Toast
                            .makeText(context, "Profil bearbeiten", Toast.LENGTH_SHORT)
                            .show()
                    }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Bewertung klickbar
            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable {
                        Toast
                            .makeText(context, "Bewertungen öffnen", Toast.LENGTH_SHORT)
                            .show()
                    },
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
                        showArrow = true,
                        onClick = {
                            Toast
                                .makeText(context, "Auto öffnen", Toast.LENGTH_SHORT)
                                .show()
                        }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    InfoRow(
                        icon = Icons.Default.Email,
                        title = "Verified",
                        value = "Email, Phone",
                        showCheck = true,
                        onClick = {
                            Toast
                                .makeText(context, "Verifizierung öffnen", Toast.LENGTH_SHORT)
                                .show()
                        }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    InfoRow(
                        icon = Icons.Default.LocationOn,
                        title = "Lives in",
                        value = "Cologne, Germany",
                        showArrow = true,
                        onClick = {
                            Toast
                                .makeText(context, "Wohnort öffnen", Toast.LENGTH_SHORT)
                                .show()
                        }
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
                    color = Color.DarkGray,
                    modifier = Modifier.clickable {
                        Toast
                            .makeText(context, "Alle Fahrten anzeigen", Toast.LENGTH_SHORT)
                            .show()
                    }
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
                        route = "Cologne → Berlin",
                        onClick = {
                            Toast
                                .makeText(context, "Fahrt Cologne → Berlin öffnen", Toast.LENGTH_SHORT)
                                .show()
                        }
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color(0xFFE0E0E0))
                    )

                    RideHistoryItem(
                        date = "05 May 2024",
                        route = "Düsseldorf → Frankfurt",
                        onClick = {
                            Toast
                                .makeText(context, "Fahrt Düsseldorf → Frankfurt öffnen", Toast.LENGTH_SHORT)
                                .show()
                        }
                    )
                }
            }
        }
    }
}