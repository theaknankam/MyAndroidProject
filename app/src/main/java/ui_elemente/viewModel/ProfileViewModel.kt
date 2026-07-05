package ui_elemente.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.carsharing_app.data.AppDatabase
import com.example.carsharing_app.data.ProfileEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val profileDao =
        AppDatabase.getDatabase(application).profileDao()

    val profile = profileDao.getProfile()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    fun saveProfile(
        name: String,
        email: String,
        phone: String,
        city: String,
        car: String,
        imageUri: String?
    ) {
        viewModelScope.launch {
            profileDao.saveProfile(
                ProfileEntity(
                    id = 1,
                    name = name,
                    email = email,
                    phone = phone,
                    city = city,
                    car = car,
                    imageUri = imageUri
                )
            )
        }
    }
}