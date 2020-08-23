package com.example.capston.pcjari.PC

import com.google.gson.annotations.SerializedName

class PCListResponse {
    @SerializedName("status")
    lateinit var status : String

    @SerializedName("num_result")
    var numResult : Int = 0

    @SerializedName("results")
    lateinit var pcList : ArrayList<PCListItem>
}