package com.example.capston.pcjari.pc

import com.google.gson.annotations.SerializedName
import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import java.io.Serializable
import kotlin.math.roundToInt

/**
 * Created by KangSeungho on 2017-09-25.
 */
class PCListItem : Serializable {
    @SerializedName("id")
    var pcID = 0

    @SerializedName("notice")
    var notice: String? = null

    @SerializedName("url")
    var icon: String? = null

    @SerializedName("name")
    var title: String? = null

    @SerializedName("si")
    var si: String? = null

    @SerializedName("gu")
    var gu: String? = null

    @SerializedName("dong")
    var dong: String? = null

    @SerializedName("etc_juso")
    var etc_juso: String? = null

    @SerializedName("tel")
    var tel: String? = null

    @SerializedName("cpu")
    var cpu: String? = null

    @SerializedName("ram")
    var ram: String? = null

    @SerializedName("vga")
    var vga: String? = null

    @SerializedName("peripheral")
    var peripheral: String? = null

    @SerializedName("price")
    var price : String? = null

    @SerializedName("card")
    var isCard = false

    @SerializedName("seatlength")
    var seatLength = 0

    @SerializedName("x")
    var location_x = 0.0

    @SerializedName("y")
    var location_y = 0.0

    @SerializedName("total")
    var totalSeat = 0

    @SerializedName("space")
    var spaceSeat = 0

    @SerializedName("using")
    var usingSeat = 0

    @SerializedName("distance")
    var dist = 0.0

    fun distToString() = when(dist) {
        0.0 -> "${dist * 1000}m"
        else -> "${(dist * 10).roundToInt().toDouble() / 10}km"
    }

    override fun toString(): String {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE)
    }
}