package com.example.carsharing_app.data

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
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
                            id = doc.getLong("originalTripId")?.toInt() ?: doc.id.hashCode(),
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

    /**
     * Réserve un trajet en débitant le portefeuille fictif de l'utilisateur.
     * Utilise une transaction Firestore pour garantir qu'on ne peut pas
     * réserver deux fois avec le même solde (double clic, etc.).
     *
     * onResult(success, message) est appelé avec le résultat pour que l'UI
     * affiche le bon message (succès ou solde insuffisant / erreur).
     */
    fun bookTrip(trip: Trip, onResult: (success: Boolean, message: String) -> Unit) {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            onResult(false, "Vous devez être connecté pour réserver")
            return
        }

        val userRef = db.collection("users").document(userId)
        val bookingRef = db.collection("bookedTrips").document()

        db.runTransaction { transaction ->
            val snapshot = transaction.get(userRef)
            val balance = snapshot.getDouble("walletBalance") ?: 0.0

            if (balance < trip.price) {
                throw IllegalStateException("Solde insuffisant (€$balance disponible, €${trip.price} requis)")
            }

            transaction.update(userRef, "walletBalance", balance - trip.price)

            transaction.set(
                bookingRef,
                hashMapOf(
                    "originalTripId" to trip.id,
                    "bookedBy" to userId,
                    "fromCity" to trip.fromCity,
                    "toCity" to trip.toCity,
                    "date" to trip.date,
                    "seats" to trip.seats,
                    "price" to trip.price,
                    "createdBy" to trip.createdBy, // ← originaler Fahrer bleibt!
                    "status" to "UPCOMING"
                )
            )

            // Trace de la transaction pour un futur historique de paiement
            transaction.set(
                db.collection("walletTransactions").document(),
                hashMapOf(
                    "userId" to userId,
                    "type" to "DEBIT",
                    "amount" to trip.price,
                    "reason" to "Booking ${trip.fromCity} → ${trip.toCity}",
                    "timestamp" to System.currentTimeMillis()
                )
            )
        }.addOnSuccessListener {
            onResult(true, "Paiement de €${trip.price} confirmé (portefeuille)")
        }.addOnFailureListener { e ->
            onResult(false, e.message ?: "Échec du paiement")
        }
    }

    /**
     * Recharge fictive du portefeuille (pas de vraie carte, juste +montant).
     */
    fun rechargeWallet(amount: Double, onResult: (success: Boolean) -> Unit) {
        val userId = auth.currentUser?.uid ?: return onResult(false)
        val userRef = db.collection("users").document(userId)

        db.runTransaction { transaction ->
            val snapshot = transaction.get(userRef)
            val balance = snapshot.getDouble("walletBalance") ?: 0.0
            transaction.update(userRef, "walletBalance", balance + amount)

            transaction.set(
                db.collection("walletTransactions").document(),
                hashMapOf(
                    "userId" to userId,
                    "type" to "CREDIT",
                    "amount" to amount,
                    "reason" to "Wallet top-up",
                    "timestamp" to System.currentTimeMillis()
                )
            )
        }.addOnSuccessListener {
            onResult(true)
        }.addOnFailureListener {
            onResult(false)
        }
    }

    fun deleteTrip(trip: Trip) {
        viewModelScope.launch {
            repository.deleteTrip(trip)
        }
    }
}