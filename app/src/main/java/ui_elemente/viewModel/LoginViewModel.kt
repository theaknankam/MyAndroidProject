package ui_elemente.viewModel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class LoginViewModel : ViewModel() {

    var username by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var errorMessage by mutableStateOf("")

    private val auth = FirebaseAuth.getInstance()

    fun onUsernameChange(value: String) { username = value }
    fun onPasswordChange(value: String) { password = value }


    //supend means they must be called from a coroutine
    suspend fun login(): Boolean {
        return try {
            auth.signInWithEmailAndPassword(username, password).await()
            //await() (from Kotlin Coroutines) wait for the Firebase result without blocking the main thread.
            true
        } catch (e: Exception) {
            errorMessage = e.message ?: "Login failed"
            false
        }
    }

    suspend fun register(): Boolean {
        return try {
            val result = auth.createUserWithEmailAndPassword(username, password).await()
            result.user?.uid?.let { uid ->
                FirebaseFirestore.getInstance().collection("users")
                    .document(uid)
                    .set(mapOf("co2Saved" to 0.0, "walletBalance" to 50.0)) // solde fictif de départ
            }
            true
        } catch (e: Exception) {
            errorMessage = e.message ?: "Register failed"
            false
        }
    }
}