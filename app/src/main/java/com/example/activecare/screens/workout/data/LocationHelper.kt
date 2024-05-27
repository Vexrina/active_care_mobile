package com.example.activecare.screens.workout.data

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.core.content.ContextCompat
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Polyline

class LocationHelper(private val context: Context) {

    private var previousLocation: Location? = null

    companion object {
        private const val MIN_TIME_BW_UPDATES = 1000L
        private const val MIN_DISTANCE_CHANGE_FOR_UPDATES = 10f
    }

    fun getCurrentLocation(
        mapView: MapView? = null,
        callback: (Location?) -> Unit,
    ) {
        // Проверяем разрешение на доступ к местоположению
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            callback(null)
            return
        }

        // Создаем LocationManager
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // Создаем LocationListener
        val locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                // Передаем текущее местоположение в колбэк
                callback(location)
                if (mapView != null) updateLocationOnMap(location, mapView = mapView)
                // Останавливаем получение обновлений местоположения после первого обновления
                locationManager.removeUpdates(this)
            }

            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}
        }

        // Запрашиваем обновления местоположения
        try {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                MIN_TIME_BW_UPDATES,
                MIN_DISTANCE_CHANGE_FOR_UPDATES,
                locationListener
            )
        } catch (ex: SecurityException) {
            callback(null)
        }
    }

    fun updateLocationOnMap(location: Location, mapView: MapView) {
        // Обновляем местоположение на карте
        mapView.controller.setCenter(GeoPoint(location.latitude, location.longitude))

        // Рисуем маршрут, если есть предыдущее местоположение
        previousLocation?.let {
            val polyline = Polyline()
            polyline.outlinePaint.color = Color.RED
            polyline.addPoint(GeoPoint(it.latitude, it.longitude))
            polyline.addPoint(GeoPoint(location.latitude, location.longitude))
            mapView.overlayManager.add(polyline)
            mapView.invalidate()
        }

        // Обновляем предыдущее местоположение
        previousLocation = location
    }
}
