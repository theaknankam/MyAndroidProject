package ui_elemente.screens

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.core.content.ContextCompat
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import ui_elemente.components.InfoRow
import ui_elemente.components.RideHistoryItem
import ui_elemente.navigation.Topbar
import ui_elemente.viewModel.ProfileViewModel
import java.io.File
import java.io.FileOutputStream

@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: ProfileViewModel = viewModel()
) {
    val context = LocalContext.current
    val profile by viewModel.profile.collectAsState()

    var name by remember { mutableStateOf("John Doe") }
    var email by remember { mutableStateOf("john@email.com") }
    var phone by remember { mutableStateOf("+49 123456789") }
    var city by remember { mutableStateOf("Cologne, Germany") }
    var car by remember { mutableStateOf("Toyota Corolla 2020") }
    var imageUri by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(profile) {
        if (profile != null) {
            name = profile!!.name
            email = profile!!.email
            phone = profile!!.phone
            city = profile!!.city
            car = profile!!.car
            imageUri = profile!!.imageUri
        }
    }

    val galleryLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.OpenDocument()
        ) { uri: Uri? ->
            if (uri != null) {
                context.contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )

                imageUri = uri.toString()

                viewModel.saveProfile(
                    name = name,
                    email = email,
                    phone = phone,
                    city = city,
                    car = car,
                    imageUri = imageUri
                )

                Toast
                    .makeText(context, "Bild aus Galerie gespeichert", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    val galleryPermissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                galleryLauncher.launch(arrayOf("image/*"))
            } else {
                Toast
                    .makeText(context, "Galerie-Erlaubnis wurde abgelehnt", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    val cameraLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicturePreview()
        ) { bitmap: Bitmap? ->
            if (bitmap != null) {
                val savedUri = saveBitmapToInternalStorage(
                    context = context,
                    bitmap = bitmap
                )

                imageUri = savedUri

                viewModel.saveProfile(
                    name = name,
                    email = email,
                    phone = phone,
                    city = city,
                    car = car,
                    imageUri = imageUri
                )

                Toast
                    .makeText(context, "Kamerabild gespeichert", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    val cameraPermissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                cameraLauncher.launch(null)
            } else {
                Toast
                    .makeText(context, "Kamera-Erlaubnis wurde abgelehnt", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    Scaffold { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
        ) {

            Topbar("Profile", navController)

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE0E0E0)),
                    contentAlignment = Alignment.Center
                ) {
                     if (imageUri != null) {
                        AsyncImage(
                            model = imageUri,
                            contentDescription = "Profile Image",
                            modifier = Modifier
                                .size(120.dp)
                                .clip(CircleShape)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile Image",
                            tint = Color.DarkGray,
                            modifier = Modifier.size(70.dp)
                        )
                    }
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

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        val galleryPermission = getGalleryPermission()

                        val permissionStatus = ContextCompat.checkSelfPermission(
                            context,
                            galleryPermission
                        )

                        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
                            galleryLauncher.launch(arrayOf("image/*"))
                        } else {
                            galleryPermissionLauncher.launch(galleryPermission)
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.PhotoLibrary,
                        contentDescription = "Gallery"
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Text("Galerie")
                }

                Spacer(modifier = Modifier.width(12.dp))

                Button(
                    onClick = {
                        val permission = ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.CAMERA
                        )

                        if (permission == PackageManager.PERMISSION_GRANTED) {
                            cameraLauncher.launch(null)
                        } else {
                            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = "Camera"
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Text("Kamera")
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = name,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(8.dp))

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
                        value = car,
                        showArrow = true,
                        onClick = {
                            Toast
                                .makeText(context, "Auto bearbeiten", Toast.LENGTH_SHORT)
                                .show()
                        }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    InfoRow(
                        icon = Icons.Default.Email,
                        title = "Verified",
                        value = "$email, $phone",
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
                        value = city,
                        showArrow = true,
                        onClick = {
                            Toast
                                .makeText(context, "Wohnort bearbeiten", Toast.LENGTH_SHORT)
                                .show()
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Profil bearbeiten",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("E-Mail") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Telefon") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                label = { Text("Wohnort") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = car,
                onValueChange = { car = it },
                label = { Text("Auto") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.saveProfile(
                        name = name,
                        email = email,
                        phone = phone,
                        city = city,
                        car = car,
                        imageUri = imageUri
                    )

                    Toast
                        .makeText(context, "Profil dauerhaft gespeichert", Toast.LENGTH_SHORT)
                        .show()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Profile")
            }

            Spacer(modifier = Modifier.height(32.dp))

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
                        navController.navigate("gebuchteRides")
                    }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

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

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

fun saveBitmapToInternalStorage(
    context: android.content.Context,
    bitmap: Bitmap
): String {
    val file = File(context.filesDir, "profile_image.png")

    FileOutputStream(file).use { outputStream ->
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
    }

    return Uri.fromFile(file).toString()
}
fun getGalleryPermission(): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }
}