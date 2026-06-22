package com.example.carsharing_app.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class TripViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TripRepository
    val allTrips: StateFlow<List<Trip>>

    init {
        val dao = AppDatabase.getDatabase(application).tripDao()
        repository = TripRepository(dao)

        allTrips = repository.allTrips.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    }

    fun addTrip(fromCity: String, toCity: String, date: String, seats: Int, price: Int) {
        viewModelScope.launch {
            repository.insertTrip(
                Trip(
                    fromCity = fromCity,
                    toCity = toCity,
                    date = date,
                    seats = seats,
                    price = price
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
