package com.example.capston.pcjari.activity.a111_location.location

import com.google.gson.annotations.SerializedName

class LocationListResponse {
    @SerializedName("status")
    lateinit var status : String

    @SerializedName("num_result")
    var numResult : Int = 0

    @SerializedName("results")
    lateinit var locationList : ArrayList<LocationListItem>
}