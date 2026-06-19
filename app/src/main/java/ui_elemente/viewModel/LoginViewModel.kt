package ui_elemente.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

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

    fun login(): Boolean {
        return username.isNotBlank() && password.isNotBlank()
    }
}