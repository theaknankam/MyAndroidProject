package com.example.carsharing_app.data

import kotlinx.coroutines.flow.Flow

class TripRepository(val tripDao: TripDao) {

    val allTrips: Flow<List<Trip>> = tripDao.getAllTrips()

    suspend fun insertTrip(trip: Trip) {
        tripDao.insertTrip(trip)
    }

    suspend fun deleteTrip(trip: Trip) {
        tripDao.deleteTrip(trip)
    }
}
