package com.example.capston.pcjari.activity.a100_main

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.capston.pcjari.PCJariApplication
import com.example.capston.pcjari.pc.PCListItem
import com.example.capston.pcjari.util.GPSTracker
import com.example.capston.pcjari.util.Preferences
import com.example.capston.pcjari.util.retrofit.BaseRetrofitCallback
import com.example.capston.pcjari.util.retrofit.RetrofitClient

class F120MainGpsFragmentViewModel : ViewModel() {
    private val retroRepos = RetrofitClient.getInstance()
    private var gpsTracker: GPSTracker? = null

    private val _location = MutableLiveData<Location>()
    val location: LiveData<Location> get() = _location

    private val _pcList = MutableLiveData<ArrayList<PCListItem>>()
    val pcList: LiveData<ArrayList<PCListItem>> get() = _pcList

    val searchName = MutableLiveData<String>()

    fun updateLocation() {
        if(gpsTracker == null)
            gpsTracker = GPSTracker(PCJariApplication.instance)

        _location.value = gpsTracker?.update()
    }

    fun getPCList(location: Location) {
        retroRepos.getPCListByGps(
            lat = location.latitude,
            lng = location.longitude,
            dist = Preferences.gps_distance.toDouble() / 10,
            name = searchName.value)
            .enqueue(object : BaseRetrofitCallback<ArrayList<PCListItem>>(PCJariApplication.instance) {
                override fun onSuccess(response: ArrayList<PCListItem>) {
                    _pcList.value = response
                }
            })
    }
}