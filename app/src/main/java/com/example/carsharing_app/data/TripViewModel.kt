package com.example.carsharing_app.data

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
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
    val bookedTrips = MutableStateFlow<List<Trip>>(emptyList())

    init {
        val dao = AppDatabase.getDatabase(application).tripDao()
        repository = TripRepository(dao)

        allTrips = repository.allTrips.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        loadFirestoreTrips()
        loadBookedTrips()
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
                            createdBy = doc.getString("createdBy") ?: "",
                            allowSmoking = doc.getBoolean("allowSmoking") ?: false,
                            allowPets = doc.getBoolean("allowPets") ?: false,
                            allowMusic = doc.getBoolean("allowMusic") ?: true,
                            ladiesOnly = doc.getBoolean("ladiesOnly") ?: false
                        )
                    }
                }
            }
    }


    private fun loadBookedTrips() {
        val userId = auth.currentUser?.uid ?: return
        db.collection("bookedTrips")
            .whereEqualTo("bookedBy", userId)
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    bookedTrips.value = snapshot.documents.mapNotNull { doc ->
                        Trip(
                            id = doc.id.hashCode(),
                            fromCity = doc.getString("fromCity") ?: "",
                            toCity = doc.getString("toCity") ?: "",
                            date = doc.getString("date") ?: "",
                            seats = doc.getLong("seats")?.toInt() ?: 0,
                            price = doc.getLong("price")?.toInt() ?: 0,
                            status = doc.getString("status") ?: "UPCOMING",
                            createdBy = doc.getString("createdBy") ?: "",
                            allowSmoking = false,
                            allowPets = false,
                            allowMusic = true,
                            ladiesOnly = false
                        )
                    }
                }
            }
    }



    fun addTrip(
        fromCity: String, 
        toCity: String, 
        date: String, 
        seats: Int, 
        price: Int,
        allowSmoking: Boolean = false,
        allowPets: Boolean = false,
        allowMusic: Boolean = true,
        ladiesOnly: Boolean = false
    ) {
        viewModelScope.launch {
            val trip = Trip(
                fromCity = fromCity,
                toCity = toCity,
                date = date,
                seats = seats,
                price = price,
                status = "UPCOMING",
                createdBy = auth.currentUser?.uid ?: "",
                allowSmoking = allowSmoking,
                allowPets = allowPets,
                allowMusic = allowMusic,
                ladiesOnly = ladiesOnly
            )
            
            repository.insertTrip(trip)

            val userId = auth.currentUser?.uid ?: "anonymous"
            db.collection("trips").add(
                hashMapOf(
                    "fromCity" to fromCity,
                    "toCity" to toCity,
                    "date" to date,
                    "seats" to seats,
                    "price" to price,
                    "status" to "UPCOMING",
                    "createdBy" to userId,
                    "allowSmoking" to allowSmoking,
                    "allowPets" to allowPets,
                    "allowMusic" to allowMusic,
                    "ladiesOnly" to ladiesOnly
                )
            )
        }
    }

   /* fun bookTrip(trip: Trip) {
        viewModelScope.launch {
            repository.insertTrip(
                trip.copy(
                    id = 0,
                    status = "UPCOMING",
                    createdBy = auth.currentUser?.uid ?: "me"
                )
            )
        }
    }*/

    fun bookTrip(trip: Trip) {
        val userId = auth.currentUser?.uid ?: return

        // In Firestore als gebuchte Fahrt speichern
        db.collection("bookedTrips").add(
            hashMapOf(
                "bookedBy" to userId,
                "fromCity" to trip.fromCity,
                "toCity" to trip.toCity,
                "date" to trip.date,
                "seats" to trip.seats,
                "price" to trip.price,
                "createdBy" to trip.createdBy,  // ← originaler Fahrer bleibt!
                "status" to "UPCOMING"
            )
        )
    }

    fun deleteTrip(trip: Trip) {
        viewModelScope.launch {
            repository.deleteTrip(trip)
        }
    }
}
