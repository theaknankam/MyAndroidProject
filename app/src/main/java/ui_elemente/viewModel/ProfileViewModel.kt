package ui_elemente.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.carsharing_app.data.AppDatabase
import com.example.carsharing_app.data.ProfileEntity
import com.example.carsharing_app.data.ProfileRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProfileViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository =ProfileRepository(
        AppDatabase.getDatabase(application).profileDao()
    )


    val profile = repository
        .getProfile()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    private val _citiesVisited = MutableStateFlow(0)
    val citiesVisited: StateFlow<Int> = _citiesVisited

    private val _co2Saved = MutableStateFlow(0.0)
    val co2Saved: StateFlow<Double> = _co2Saved

    // Cities visited: distinct destination cities across this user's trips
    /*val citiesVisited: StateFlow<Int> = tripDao.getAllTrips()
        .map { trips ->
            val uid = auth.currentUser?.uid ?: return@map 0
            trips.filter { it.createdBy == uid }
                .map { it.toCity }
                .distinct()
                .size
        }*
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)*/



    // ← nur ein init Block!
    init {
        auth.currentUser?.uid?.let { uid ->

            // CO2 aus Firestore
            firestore.collection("users").document(uid)
                .addSnapshotListener { snapshot, _ ->
                    _co2Saved.value = snapshot?.getDouble("co2Saved") ?: 0.0
                }

            // Cities visited aus Firestore
            firestore.collection("trips")
                .whereEqualTo("createdBy", uid)
                .addSnapshotListener { snapshot, _ ->
                    if (snapshot != null) {
                        _citiesVisited.value = snapshot.documents
                            .mapNotNull { it.getString("toCity") }
                            .distinct()
                            .size
                    }
                }
        }
    }
    fun saveProfile(
        name: String,
        email: String,
        phone: String,
        city: String,
        car: String,
        imageUri: String?
    ) {
        viewModelScope.launch {
            repository.saveProfile(
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