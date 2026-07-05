package com.example.carsharing_app.Karte

import retrofit2.Retrofit
import kotlin.jvm.java


import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

data class OsrmResponse(val routes: List<OsrmRoute>)
data class OsrmRoute(val geometry: OsrmGeometry)
data class OsrmGeometry(val coordinates: List<List<Double>>)


interface OSMRApi {@GET("route/v1/driving/{coords}")
suspend fun getRoute(
    @Path("coords") coords: String,
    @Query("geometries") geometries: String = "geojson",
    @Query("overview") overview: String = "full"
): OsrmResponse
}

object OsrmClient {
    val api: OSMRApi = Retrofit.Builder()
        .baseUrl("https://router.project-osrm.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(OSMRApi::class.java)
}