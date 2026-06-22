package com.example.carsharing_app.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TripDao {

    @Insert
    suspend fun insertTrip(trip: Trip)

    @Query("SELECT * FROM trips ORDER BY id DESC")
    fun getAllTrips(): Flow<List<Trip>>

    @Query("SELECT * FROM trips WHERE fromCity = :from AND toCity = :to")
    fun getMatchingTrips(from: String, to: String): Flow<List<Trip>>

    @Delete
    suspend fun deleteTrip(trip: Trip)
}