package com.example.capston.pcjari.activity.a210_seat.seat

import com.google.gson.annotations.SerializedName

class SeatResponse {
    @SerializedName("status")
    lateinit var status : String

    @SerializedName("num_result")
    var numResult : Int = 0

    @SerializedName("results")
    lateinit var seatList : ArrayList<Seat>
}