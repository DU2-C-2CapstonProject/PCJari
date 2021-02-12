package com.example.capston.pcjari.Activity.A111_Location.Location

import com.google.gson.annotations.SerializedName

class LocationListResponse {
    @SerializedName("status")
    lateinit var status : String

    @SerializedName("num_result")
    var numResult : Int = 0

    @SerializedName("results")
    lateinit var locationList : ArrayList<LocationListItem>
}