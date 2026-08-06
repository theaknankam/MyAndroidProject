package ui_elemente.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import ui_elemente.viewModel.RegistrationViewModel

@Composable
fun RegistrationScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    registrationVm: RegistrationViewModel = viewModel()
){
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        OutlinedTextField(
            value = registrationVm.username,
            onValueChange = registrationVm::onUsernameChange,
            label = { Text("Username") },
            leadingIcon = { Icon(Icons.Default.Person, null) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = registrationVm.email,
            onValueChange = registrationVm::onEmailChange,
            label = { Text("Email") },
            leadingIcon = { Icon(Icons.Default.Email, null) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = registrationVm.password,
            onValueChange = registrationVm::onPasswordChange,
            label = { Text("Password") },
            leadingIcon = { Icon(Icons.Default.Lock, null) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = registrationVm.name,
            onValueChange = registrationVm::onNameChange,
            label = { Text("Name") },
            leadingIcon = { Icon(Icons.Default.Person, null) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = registrationVm.lastname,
            onValueChange = registrationVm::onLastnameChange,
            label = { Text("Lastname") },
            leadingIcon = { Icon(Icons.Default.Person, null) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = registrationVm.number,
            onValueChange = registrationVm::onNumberChange,
            label = { Text("Number") },
            leadingIcon = { Icon(Icons.Default.Phone, null) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = registrationVm.address,
            onValueChange = registrationVm::onAddressChange,
            label = { Text("Address") },
            leadingIcon = { Icon(Icons.Default.Home, null) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = registrationVm.age,
            onValueChange = registrationVm::onAgeChange,
            label = { Text("Age") },
            leadingIcon = { Icon(Icons.Default.Cake, null) },
            modifier = Modifier.fillMaxWidth()
        )

        if (registrationVm.errorMessage.isNotEmpty()) {
            Text(
                text = registrationVm.errorMessage,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                scope.launch {
                    if (registrationVm.register()) {
                        navController.popBackStack()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Register")
        }
    }
}