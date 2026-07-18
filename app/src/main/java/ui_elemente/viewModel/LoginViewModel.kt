package ui_elemente.viewModel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class LoginViewModel : ViewModel() {  // ← kein AndroidViewModel mehr nötig

    var username by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var errorMessage by mutableStateOf("")

    private val auth = FirebaseAuth.getInstance()

    fun onUsernameChange(value: String) { username = value }
    fun onPasswordChange(value: String) { password = value }

    suspend fun login(): Boolean {
        return try {
            auth.signInWithEmailAndPassword(username, password).await()
            true
        } catch (e: Exception) {
            errorMessage = e.message ?: "Login fehlgeschlagen"
            false
        }
    }

    suspend fun register(): Boolean {
        return try {
            auth.createUserWithEmailAndPassword(username, password).await()
            true
        } catch (e: Exception) {
            errorMessage = e.message ?: "Registrierung fehlgeschlagen"
            false
        }
    }
}