package ui_elemente.viewModel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class LoginViewModel : ViewModel() {  // ← kein AndroidViewModel mehr nötig

    var username by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var name by mutableStateOf("")
       private set

    var mobileNumber by mutableStateOf("")
       private set
    var errorMessage by mutableStateOf("")

    private val auth = FirebaseAuth.getInstance()

    fun onUsernameChange(value: String) { username = value }
    fun onPasswordChange(value: String) { password = value }
    fun onNameChange(value: String) { name = value }
    fun onMobileNumberChange(value: String) { mobileNumber = value }

    suspend fun login(): Boolean {
        return try {
            auth.signInWithEmailAndPassword(username, password).await()
            true
        } catch (e: Exception) {
            errorMessage = e.message ?: "Login failed"
            false
        }
    }

    suspend fun register(): Boolean {
        // Einfache Validierung der Pflichtfelder
        if (name.isBlank()) {
            errorMessage = "Bitte Namen eingeben"
            return false
        }
        if (mobileNumber.isBlank()) {
            errorMessage = "Bitte Mobilnummer eingeben"
            return false
        }
        return try {
            val result = auth.createUserWithEmailAndPassword(username, password).await()
            result.user?.uid?.let { uid ->
                FirebaseFirestore.getInstance().collection("users")
                    .document(uid)
                    .set(
                        mapOf(
                            "name" to name,
                            "mobileNumber" to mobileNumber,
                            "email" to username,
                            "co2Saved" to 0.0
                        )
                    )
            }
            true
        } catch (e: Exception) {
            errorMessage = e.message ?: "Register failed"
            false
        }
    }
}