package com.example.geojournal.util

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Handler
import android.os.Looper
import java.util.Locale

// This object changes numbers (latitude and longitude) into a place name like "Redcar, United Kingdom".
object PlaceHelper {

    fun getPlaceName(
        context: Context,
        latitude: Double,
        longitude: Double,
        onResult: (String) -> Unit
    ) {
        // If no name can be found, the rounded numbers are shown instead
        val fallback = "Lat " + "%.2f".format(latitude) + ", Lng " + "%.2f".format(longitude)

        // results must come back on the main thread because they change the screen
        val mainHandler = Handler(Looper.getMainLooper())

        if (!Geocoder.isPresent()) {
            onResult(fallback)
            return
        }

        val geocoder = Geocoder(context, Locale.getDefault())

        try {
            if (Build.VERSION.SDK_INT >= 33) {
                // newer phones use a listener
                geocoder.getFromLocation(latitude, longitude, 1, object : Geocoder.GeocodeListener {
                    override fun onGeocode(addresses: MutableList<Address>) {
                        val name = makeName(addresses.firstOrNull(), fallback)
                        mainHandler.post { onResult(name) }
                    }

                    override fun onError(errorMessage: String?) {
                        mainHandler.post { onResult(fallback) }
                    }
                })
            } else {
                // older phones use the old way, which must run in the background
                Thread {
                    val name = try {
                        @Suppress("DEPRECATION")
                        val list = geocoder.getFromLocation(latitude, longitude, 1)
                        makeName(list?.firstOrNull(), fallback)
                    } catch (e: Exception) {
                        fallback
                    }
                    mainHandler.post { onResult(name) }
                }.start()
            }
        } catch (e: Exception) {
            onResult(fallback)
        }
    }

    // builds a short name from the address: city and country
    private fun makeName(address: Address?, fallback: String): String {
        if (address == null) return fallback

        val city = address.locality ?: address.subAdminArea
        val country = address.countryName

        return when {
            city != null && country != null -> "$city, $country"
            city != null -> city
            country != null -> country
            else -> fallback
        }
    }
}