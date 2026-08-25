package ui_elemente.screens


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ui_elemente.viewModel.LoginViewModel
import com.example.carsharing_app.R
import kotlinx.coroutines.launch
import ui_elemente.viewModel.RegistrationViewModel


@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = viewModel(),
    registerviewModel: RegistrationViewModel = viewModel()

) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var isRegisterMode by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = if (isRegisterMode) "CarSharing Registrierung" else "CarSharing Login",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(32.dp))


        Spacer(modifier = Modifier.height(16.dp))

        // Zusatzfelder nur im Registrierungs-Modus
        if (isRegisterMode) {
            OutlinedTextField(
                value = viewModel.name,
                onValueChange = viewModel::onNameChange,
                label = { Text("Name") },
                leadingIcon = { Icon(Icons.Default.Person, null) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = viewModel.mobileNumber,
                onValueChange = viewModel::onMobileNumberChange,
                label = { Text("Mobilnummer") },
                leadingIcon = { Icon(Icons.Default.Phone, null) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        OutlinedTextField(
            value = viewModel.username,
            onValueChange = viewModel::onUsernameChange,
            label = { Text("Email") },
            leadingIcon = { Icon(Icons.Default.Person, null) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = viewModel.password,
            onValueChange = viewModel::onPasswordChange,
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            leadingIcon = {
                Icon(Icons.Default.Lock, null)
            },
            modifier = Modifier.fillMaxWidth()
        )

        if (viewModel.errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(viewModel.errorMessage, color = Color.Red)
        }

        Spacer(modifier = Modifier.height(24.dp))


        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {

                scope.launch {
                    val success = if (isRegisterMode) viewModel.register() else viewModel.login()

                    if (success) {

                        onLoginSuccess()

                    } else {

                        Toast.makeText(context, viewModel.errorMessage, Toast.LENGTH_SHORT).show()
                    }

                }

            }
        ) {
            Text(
                text = if (isRegisterMode) "Register" else "Login",
                fontSize = 20.sp
            )
        }
        Spacer(modifier = Modifier.height(12.dp))

        TextButton(
            onClick = { isRegisterMode = !isRegisterMode }
        ) {
            Text(
                if (isRegisterMode) "Schon registriert? Zum Login"
                else "Noch kein Konto? Jetzt registrieren"
            )
        }
    }
}