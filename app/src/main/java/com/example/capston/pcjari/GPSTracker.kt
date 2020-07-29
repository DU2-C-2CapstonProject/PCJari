package com.example.capston.pcjari

import android.Manifest
import android.app.AlertDialog
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import androidx.core.content.ContextCompat

/**
 * Created by KangSeungho on 2017-11-04.
 */
class GPSTracker(private val mContext: Context) : Service(), LocationListener {

    // flag for GPS status
    var isGPSEnabled = false

    // flag for network status
    var isNetworkEnabled = false

    // flag for GPS status
    var canGetLocation = false
    var location: Location? = null
    var latitude = 0.0 // latitude
    var longitude = 0.0 // longitude

    // Declaring a Location Manager
    protected var locationManager: LocationManager? = null
    fun Update() {
        getLocation()
    }

    fun getLocation() {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        try {
            locationManager = mContext
                    .getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (locationManager != null) {
                // getting GPS status
                isGPSEnabled = locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)

                // getting network status
                isNetworkEnabled = locationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
                if (!isGPSEnabled && !isNetworkEnabled) {
                    // no network provider is enabled
                } else {
                    canGetLocation = true
                    // First get location from Network Provider
                    if (isNetworkEnabled) {
                        locationManager!!.requestLocationUpdates(
                                LocationManager.NETWORK_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(), this)

                        Log.d("Network", "Network")

                        if (locationManager != null) {
                            location = locationManager!!.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                            if (location != null) {
                                latitude = location!!.latitude
                                longitude = location!!.longitude
                            }
                        }
                    }
                    // if GPS Enabled get lat/long using GPS Services
                    if (isGPSEnabled) {
                        if (location == null) {
                            locationManager!!.requestLocationUpdates(
                                    LocationManager.GPS_PROVIDER,
                                    MIN_TIME_BW_UPDATES,
                                    MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(), this)
                            Log.d("GPS Enabled", "GPS Enabled")
                            if (locationManager != null) {
                                location = locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                                if (location != null) {
                                    latitude = location!!.latitude
                                    longitude = location!!.longitude
                                }
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     */
    fun stopUsingGPS() {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }

        locationManager?.removeUpdates(this@GPSTracker)
    }

    /**
     * Function to get latitude
     */
    fun getLatitude(): Double? {
        latitude = location!!.latitude

        // return latitude
        return latitude
    }

    /**
     * Function to get longitude
     */
    fun getLongitude(): Double? {
        longitude = location!!.longitude

        // return longitude
        return longitude
    }

    /**
     * Function to check GPS/wifi enabled
     * @return boolean
     */
    fun canGetLocation(): Boolean {
        return canGetLocation
    }

    /**
     * Function to show settings alert dialog
     * On pressing Settings button will lauch Settings Options
     */
    fun showSettingsAlert() {
        val alertDialog = AlertDialog.Builder(mContext)
        alertDialog.setTitle("위치 서비스 설정")
        alertDialog.setMessage("무선 네트워크, GPS 중 한 가지 이상 체크하셔야 정확한 위치 서비스가 가능합니다.\n위치 서비스 기능을 설정하시겠습니까?")

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings") { dialog, which ->
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            mContext.startActivity(intent)
        }

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }

        // Showing Alert Message
        alertDialog.show()
    }

    override fun onLocationChanged(location: Location?) {
        if (location != null) {
            val latitude = location.latitude
            val longitude = location.longitude
        }
    }

    override fun onProviderDisabled(provider: String?) {}
    override fun onProviderEnabled(provider: String?) {}
    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
    override fun onBind(arg0: Intent?): IBinder? {
        return null
    }

    companion object {
        // The minimum distance to change Updates in meters
        private const val MIN_DISTANCE_CHANGE_FOR_UPDATES: Long = 10 // 10 meters

        // The minimum time between updates in milliseconds
        private const val MIN_TIME_BW_UPDATES = 1000 * 60 * 1 // 1 minute
                .toLong()
    }

    init {
        getLocation()
    }
}