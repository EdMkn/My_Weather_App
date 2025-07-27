package com.myprojects.my_weather_app.data

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CompletableDeferred

class LocationHelper(private val context: Context) {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    suspend fun getCurrentLocation(): Location? {
        return try {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {

                val locationResult = CompletableDeferred<Location?>()

                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location ->
                        locationResult.complete(location)
                    }
                    .addOnFailureListener {
                        locationResult.complete(null)
                    }

                locationResult.await()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}