package com.example.weather.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class LocationHelper(context: Context) {
    // Initializes the FusedLocationProviderClient to access location services.
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    suspend fun getLastKnownLocation(): Location? = suspendCancellableCoroutine { continuation ->
        // Request the last known location using the FusedLocationProviderClient.
        val locationTask: Task<Location> = fusedLocationClient.lastLocation
        locationTask.addOnSuccessListener { location ->
            continuation.resume(location)
        }

        // If the location is successfully retrieved, resume the coroutine with the location.
        locationTask.addOnFailureListener { exception ->
            continuation.resumeWithException(exception)
        }
    }
}
