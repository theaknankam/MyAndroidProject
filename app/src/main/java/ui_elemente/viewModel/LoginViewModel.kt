package ui_elemente.viewModel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.carsharing_app.data.AppDatabase
import ui_elemente.Repository.UserRepository

class LoginViewModel(application: Context) : ViewModel() {

    var username by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    fun onUsernameChange(value: String) {
        username = value
    }

    fun onPasswordChange(value: String) {
        password = value
    }
    private val repository = UserRepository(
        AppDatabase.getDatabase(application).userDao()
    )
    suspend fun login(): Boolean {

        return repository.login(
            username,
            password
        ) != null
    }
}