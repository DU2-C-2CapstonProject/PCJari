package com.example.capston.pcjari.activity.a210_seat.seat

import com.google.gson.annotations.SerializedName
import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by KangSeungho on 2017-12-01.
 */
class Seat : Serializable {
    companion object {
        // 좌석 종류
        const val SEAT_ID_SEAT        = 1
        const val SEAT_ID_HALL        = 0
        const val SEAT_ID_SMOKE       = -1
        const val SEAT_ID_COUNTER     = -2

        // 좌석 상태
        const val PC_STATE_EMPTY      = 0     // 빈 자리
        const val PC_STATE_PREPAID    = 1     // 선불
        const val PC_STATE_POSTPAID   = 2     // 후불
    }

    @SerializedName("pc_id")
    var pcId = 0

    @SerializedName("place_id")
    lateinit var placeId : String

    @SerializedName("seat_id")
    var seatId = 0

    @SerializedName("state")
    var pcState = 0

    @SerializedName("time")
    var pcTime = 0

    fun isEmptySeat() : Boolean {
        return pcState == PC_STATE_EMPTY
    }

    fun isPrepaidSeat() : Boolean {
        return pcState == PC_STATE_PREPAID
    }

    fun isPostpaidSeat() : Boolean {
        return pcState == PC_STATE_POSTPAID
    }

    fun getDateTime() : String {
        return SimpleDateFormat("HH:mm").format(Date((pcTime * 60 * 1000).toLong()))
    }

    override fun toString(): String {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE)
    }
}