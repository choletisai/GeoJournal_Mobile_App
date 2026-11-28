package com.example.geojournal.util

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource

// This object finds where the phone is right now.
object LocationHelper {

    // The screen checks the permission BEFORE calling this function,
    // so the warning is switched off here.
    @SuppressLint("MissingPermission")
    fun getCurrentLocation(context: Context, onResult: (Location?) -> Unit) {
        val client = LocationServices.getFusedLocationProviderClient(context)
        val tokenSource = CancellationTokenSource()

        // getCurrentLocation takes ONE reading only.
        // It does not keep tracking the user, so it saves battery and protects privacy.
        client.getCurrentLocation(
            Priority.PRIORITY_BALANCED_POWER_ACCURACY,
            tokenSource.token
        )
            .addOnSuccessListener { location -> onResult(location) }
            .addOnFailureListener { onResult(null) }
    }
}