package ui_elemente.viewModel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.carsharing_app.data.AppDatabase
import ui_elemente.Repository.UserRepository

class LoginViewModel(application: Application) : AndroidViewModel(application) {

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