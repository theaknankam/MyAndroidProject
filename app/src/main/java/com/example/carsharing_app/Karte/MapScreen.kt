package com.example.carsharing_app.Karte

import android.graphics.Color
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import kotlinx.coroutines.launch
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.BoundingBox
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline


@Composable
fun MapScreen(
    pointA: GeoPoint,  // z.B. GeoPoint(50.938, 6.960) = Köln
    pointB: GeoPoint
) {
    val context = LocalContext.current
    var routePoints by remember { mutableStateOf<List<GeoPoint>>(emptyList()) }

    // Route laden
    LaunchedEffect(pointA, pointB) {
        try {
            val coords = "${pointA.longitude},${pointA.latitude};${pointB.longitude},${pointB.latitude}"
            val response = OsrmClient.api.getRoute(coords)
            routePoints = response.routes.firstOrNull()
                ?.geometry?.coordinates
                ?.map { GeoPoint(it[1], it[0]) }
                ?: emptyList()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    AndroidView(
        factory = {
            MapView(context).apply {
                setTileSource(TileSourceFactory.MAPNIK)
                setMultiTouchControls(true)
                controller.setZoom(10.0)
                controller.setCenter(pointA)
            }
        },
        update = { mapView ->
            mapView.overlays.clear()

            if (routePoints.isNotEmpty()) {
                // Route-Linie
                val polyline = Polyline().apply {
                    setPoints(routePoints)
                    outlinePaint.color = android.graphics.Color.parseColor("#333333")
                    outlinePaint.strokeWidth = 10f
                }
                mapView.overlays.add(polyline)
            }

            // Marker A
            val markerA = Marker(mapView).apply {
                position = pointA
                title = "Start"
                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            }

            // Marker B
            val markerB = Marker(mapView).apply {
                position = pointB
                title = "Ziel"
                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            }

            mapView.overlays.addAll(listOf(markerA, markerB))

            // Zoom auf beide Punkte
            if (routePoints.isNotEmpty()) {
                val boundingBox = BoundingBox.fromGeoPoints(listOf(pointA, pointB))
                mapView.zoomToBoundingBox(boundingBox.increaseByScale(1.3f), true)
            }

            mapView.invalidate()
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .clip(RoundedCornerShape(16.dp))
    )
}