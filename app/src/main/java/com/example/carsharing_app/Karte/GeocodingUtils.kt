package com.example.carsharing_app.Karte

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.osmdroid.util.GeoPoint
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

suspend fun geocode(cityName: String): GeoPoint? {
    return withContext(Dispatchers.IO) {
        try {
            val encoded = URLEncoder.encode(cityName, "UTF-8")
            val url = URL("https://nominatim.openstreetmap.org/search?q=$encoded&format=json&limit=1")

            val connection = url.openConnection() as HttpURLConnection
            connection.setRequestProperty("User-Agent", "CarSharingApp/1.0")
            connection.connectTimeout = 5000
            connection.readTimeout = 5000

            val response = connection.inputStream.bufferedReader().readText()
            val json = JSONArray(response)

            if (json.length() > 0) {
                val obj = json.getJSONObject(0)
                GeoPoint(obj.getDouble("lat"), obj.getDouble("lon"))
            } else null
        } catch (e: Exception) {
            println("GEOCODE ERROR: ${e::class.simpleName}: ${e.message}")
            null
        }
    }
}

/**
 * Reverse geocoding: turns coordinates into a human-readable address string.
 * Used e.g. to show the current address on the SOS / emergency screen.
 */
suspend fun reverseGeocode(latitude: Double, longitude: Double): String? {
    return withContext(Dispatchers.IO) {
        try {
            val url = URL(
                "https://nominatim.openstreetmap.org/reverse?lat=$latitude&lon=$longitude&format=json"
            )

            val connection = url.openConnection() as HttpURLConnection
            connection.setRequestProperty("User-Agent", "CarSharingApp/1.0")
            connection.connectTimeout = 5000
            connection.readTimeout = 5000

            val response = connection.inputStream.bufferedReader().readText()
            val json = org.json.JSONObject(response)
            json.optString("display_name").ifBlank { null }
        } catch (e: Exception) {
            println("REVERSE GEOCODE ERROR: ${e::class.simpleName}: ${e.message}")
            null
        }
    }
}