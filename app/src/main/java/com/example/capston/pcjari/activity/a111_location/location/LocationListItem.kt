package com.example.capston.pcjari.activity.a111_location.location

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class LocationListItem() : Serializable {
    @SerializedName("si")
    lateinit var si : String

    @SerializedName("gu")
    lateinit var gu : String

    @SerializedName("dong")
    lateinit var dong : String

    constructor(si : String, gu : String, dong : String) : this() {
        this.si = si
        this.gu = gu
        this.dong = dong
    }
}