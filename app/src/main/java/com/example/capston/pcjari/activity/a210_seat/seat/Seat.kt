package com.example.capston.pcjari.activity.a210_seat.seat

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by KangSeungho on 2017-12-01.
 */
class Seat : Serializable {
    @SerializedName("pc_id")
    var pc_id = 0

    @SerializedName("place_id")
    var place = 0

    @SerializedName("seat_id")
    var seat_id = 0

    @SerializedName("state")
    var pc_state = 0

    @SerializedName("time")
    var pc_time = 0
}