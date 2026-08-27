package ui_elemente.screens

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Engineering
import androidx.compose.material.icons.filled.GppMaybe
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.LocalPolice
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.carsharing_app.Karte.reverseGeocode
import com.example.carsharing_app.R
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.tasks.await
import ui_elemente.navigation.Topbar

private fun hasLocationPermission(context: android.content.Context): Boolean {
    val fine = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
    val coarse = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
    return fine == PackageManager.PERMISSION_GRANTED || coarse == PackageManager.PERMISSION_GRANTED
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SOSScreen(navController: NavHostController) {
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    var hasPermission by remember { mutableStateOf(hasLocationPermission(context)) }
    var latitude by remember { mutableStateOf<Double?>(null) }
    var longitude by remember { mutableStateOf<Double?>(null) }
    var address by remember { mutableStateOf<String?>(null) }
    var isLoadingLocation by remember { mutableStateOf(false) }
    var locationError by remember { mutableStateOf<String?>(null) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        hasPermission = permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) ||
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false)
        if (!hasPermission) {
            locationError = "Location permission denied. We can't show your position to emergency services."
        }
    }

    LaunchedEffect(Unit) {
        if (!hasPermission) {
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    LaunchedEffect(hasPermission) {
        if (hasPermission) {
            isLoadingLocation = true
            locationError = null
            try {
                @Suppress("MissingPermission")
                val location = fusedLocationClient
                    .getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
                    .await()

                if (location != null) {
                    latitude = location.latitude
                    longitude = location.longitude
                    address = reverseGeocode(location.latitude, location.longitude)
                } else {
                    locationError = "Couldn't determine your current location. Make sure GPS is enabled."
                }
            } catch (e: Exception) {
                locationError = "Couldn't determine your current location. Make sure GPS is enabled."
            } finally {
                isLoadingLocation = false
            }
        }
    }

    fun makeCall(number: String) {
        val u = Uri.parse("tel:$number")
        val i = Intent(Intent.ACTION_DIAL, u)
        try {
            context.startActivity(i)
        } catch (e: Exception) {
            Toast.makeText(context, "Unable to open dialer", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            Topbar("Emergency Assistance", navController)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF8F9FA))
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Emergency Banner
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.GppMaybe,
                        contentDescription = null,
                        tint = Color.Red,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(Modifier.width(12.dp))
                    Text(
                        text = if (hasPermission)
                            "Your location is being shared with emergency services."
                        else
                            "Allow location access so emergency services can find you.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFFB71C1C),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(Modifier.height(32.dp))

            // Main SOS Pulse Button
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(220.dp)
            ) {
                // Outer circles for pulse effect decoration
                Surface(
                    modifier = Modifier.size(200.dp),
                    shape = CircleShape,
                    color = Color.Red.copy(alpha = 0.1f)
                ) {}
                Surface(
                    modifier = Modifier.size(170.dp),
                    shape = CircleShape,
                    color = Color.Red.copy(alpha = 0.2f)
                ) {}

                // Actual Button
                Button(
                    onClick = { makeCall("112") },
                    modifier = Modifier.size(140.dp),
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_emergency_white),
                            contentDescription = null,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = "SOS",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }

            Spacer(Modifier.height(32.dp))

            // Help Message
            Text(
                text = "You're not alone. We're here to help.",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(32.dp))

            // Quick Help Section
            Text(
                text = "Quick Help",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                EmergencyActionCard(
                    modifier = Modifier.weight(1f),
                    label = "Police",
                    icon = Icons.Default.LocalPolice,
                    color = Color(0xFF1976D2),
                    onClick = { makeCall("110") }
                )
                EmergencyActionCard(
                    modifier = Modifier.weight(1f),
                    label = "Ambulance",
                    icon = Icons.Default.LocalHospital,
                    color = Color(0xFF388E3C),
                    onClick = { makeCall("112") }
                )
                EmergencyActionCard(
                    modifier = Modifier.weight(1f),
                    label = "Roadside",
                    icon = Icons.Default.Engineering,
                    color = Color(0xFFF57C00),
                    onClick = { makeCall("123456789") } // Placeholder for roadside assistance
                )
            }

            Spacer(Modifier.height(24.dp))

            // Location Info Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.LocationOn, contentDescription = null, tint = Color.Gray)
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = "Current Location",
                            style = MaterialTheme.typography.labelLarge,
                            color = Color.Gray
                        )
                    }
                    Spacer(Modifier.height(8.dp))

                    when {
                        !hasPermission -> {
                            Text(
                                text = "Location permission not granted.",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        isLoadingLocation -> {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 2.dp)
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    text = "Locating you…",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                        locationError != null -> {
                            Text(
                                text = locationError ?: "",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        latitude != null && longitude != null -> {
                            Text(
                                text = address ?: "Address unavailable",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = "Lat: %.4f, Long: %.4f".format(latitude, longitude),
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            Text(
                text = "Important Information",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Stay calm and keep the call going. Someone will stay on the line with you and can contact your trusted or emergency contact if needed.",
                style = MaterialTheme.typography.bodyMedium,
                lineHeight = 22.sp,
                color = Color.DarkGray
            )

            Spacer(Modifier.height(32.dp))
        }
    }
}

@Composable
fun EmergencyActionCard(
    modifier: Modifier,
    label: String,
    icon: ImageVector,
    color: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.height(100.dp),
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(32.dp))
            Spacer(Modifier.height(8.dp))
            Text(text = label, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Preview(name = "SOS", showBackground = true)
@Composable
fun SOSScreenPreview() {
    val navController = rememberNavController()
    SOSScreen(navController = navController)
}
