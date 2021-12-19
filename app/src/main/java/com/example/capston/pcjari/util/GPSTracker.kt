package com.example.capston.pcjari.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat

/**
 * Created by KangSeungho on 2017-11-04.
 */
class GPSTracker(private val mContext: Context) {

    // flag for GPS status
    private var isGPSEnabled = false

    // flag for network status
    private var isNetworkEnabled = false

    // flag for GPS status
    var canGetLocation = false

    // Declaring a Location Manager
    private var locationManager: LocationManager? = null

    init {
        update()
    }

    fun update(): Location? {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return null

        var location: Location? = null

        locationManager = mContext
            .getSystemService(Context.LOCATION_SERVICE) as LocationManager

        locationManager?.run {
            // getting GPS status
            isGPSEnabled = isProviderEnabled(LocationManager.GPS_PROVIDER)
            if(isGPSEnabled)
                location = getLastKnownLocation(LocationManager.GPS_PROVIDER)

            // getting network status
            isNetworkEnabled = isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            if(isNetworkEnabled)
                location = getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        }

        if(location != null)
            canGetLocation = true

        return location
    }
}