package ui_elemente.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import ui_elemente.model.User

class RegistrationViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    var username by mutableStateOf("")
        private set

    var name by mutableStateOf("")
        private set

    var lastname by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var email by mutableStateOf("")
        private set

    var number by mutableStateOf("")
        private set

    var age by mutableStateOf("")
        private set

    var address by mutableStateOf("")
        private set

    var errorMessage by mutableStateOf("")

    // Update functions for the UI
    fun onUsernameChange(value: String) { username = value }
    fun onNameChange(value: String) { name = value }
    fun onLastnameChange(value: String) { lastname = value }
    fun onPasswordChange(value: String) { password = value }
    fun onEmailChange(value: String) { email = value }
    fun onNumberChange(value: String) { number = value }
    fun onAgeChange(value: String) { age = value }
    fun onAddressChange(value: String) { address = value }

    suspend fun register(): Boolean {
        return try {
            // Firebase Auth uses email and password
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            result.user?.uid?.let { uid ->
                // Create user profile in Firestore
                val userData = mapOf(
                    "username" to username,
                    "name" to name,
                    "lastname" to lastname,
                    "email" to email,
                    "number" to number,
                    "age" to age,
                    "address" to address,
                    "co2Saved" to 0.0
                )
                firestore.collection("users")
                    .document(uid)
                    .set(userData)
                    .await()
            }
            true
        } catch (e: Exception) {
            errorMessage = e.message ?: "Register failed"
            false
        }
    }
}

