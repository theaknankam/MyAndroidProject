package com.example.carsharing_app.data

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class TripViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TripRepository
    val allTrips: StateFlow<List<Trip>>
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    var firestoreTrips by mutableStateOf(listOf<Trip>())

    init {  // ← alles was beim Start passiert kommt hierher
        val dao = AppDatabase.getDatabase(application).tripDao()
        repository = TripRepository(dao)

        allTrips = repository.allTrips.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        loadFirestoreTrips()  // ← hier aufrufen, nicht außerhalb
    }


    private fun loadFirestoreTrips() {
        db.collection("trips")
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    firestoreTrips = snapshot.documents.mapNotNull { doc ->
                        Trip(
                            id = doc.getLong("id")?.toInt() ?: 0,
                            fromCity = doc.getString("fromCity") ?: "",
                            toCity = doc.getString("toCity") ?: "",
                            date = doc.getString("date") ?: "",
                            seats = doc.getLong("seats")?.toInt() ?: 0,
                            price = doc.getLong("price")?.toInt() ?: 0,
                            status = doc.getString("status") ?: "UPCOMING",
                            createdBy = doc.getString("createdBy") ?: ""
                        )
                    }
                }
            }
    }

    fun addTrip(fromCity: String, toCity: String, date: String, seats: Int, price: Int) {
        viewModelScope.launch {
            // Lokal in Room speichern
            repository.insertTrip(
                Trip(
                    fromCity = fromCity,
                    toCity = toCity,
                    date = date,
                    seats = seats,
                    price = price,
                    status = "UPCOMING",
                    createdBy = auth.currentUser?.uid ?: ""
                )
            )

            // In Firestore speichern damit andere Nutzer es sehen
            val userId = auth.currentUser?.uid ?: return@launch
            db.collection("trips").add(
                hashMapOf(
                    "fromCity" to fromCity,
                    "toCity" to toCity,
                    "date" to date,
                    "seats" to seats,
                    "price" to price,
                    "status" to "UPCOMING",
                    "createdBy" to userId
                )
            )
        }
    }

    fun bookTrip(trip: Trip) {
        viewModelScope.launch {
            // In Room speichern als gebuchte Fahrt
            repository.insertTrip(
                trip.copy(
                    id = 0,  // neue ID generieren
                    status = "UPCOMING",
                    createdBy = auth.currentUser?.uid ?: ""
                )
            )
        }
    }

    fun deleteTrip(trip: Trip) {
        viewModelScope.launch {
            repository.deleteTrip(trip)
        }
    }
}
