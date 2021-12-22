package com.example.capston.pcjari.pc

import com.google.gson.annotations.SerializedName
import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import java.io.Serializable
import kotlin.math.roundToInt

/**
 * Created by KangSeungho on 2017-09-25.
 */
data class PCListItem(
    @SerializedName("id")
    val pcID: Int,

    @SerializedName("notice")
    val notice: String?,

    @SerializedName("url")
    val icon: String?,

    @SerializedName("name")
    val title: String,

    @SerializedName("si")
    val si: String,

    @SerializedName("gu")
    val gu: String,

    @SerializedName("dong")
    val dong: String,

    @SerializedName("etc_juso")
    val etc_juso: String? = null,

    @SerializedName("tel")
    val tel: String? = null,

    @SerializedName("cpu")
    val cpu: String? = null,

    @SerializedName("ram")
    val ram: String? = null,

    @SerializedName("vga")
    val vga: String? = null,

    @SerializedName("peripheral")
    val peripheral: String? = null,

    @SerializedName("price")
    val price : String? = null,

    @SerializedName("card")
    val isCard: Boolean,

    @SerializedName("seatlength")
    val seatLength: Int,

    @SerializedName("x")
    val location_x: Double,

    @SerializedName("y")
    val location_y: Double,

    @SerializedName("total")
    val totalSeat: Int,

    @SerializedName("space")
    val spaceSeat: Int,

    @SerializedName("using")
    val usingSeat: Int,

    @SerializedName("distance")
    val dist: Double
) : Serializable {
    fun distToString() = when(dist) {
        0.0 -> "${dist * 1000}m"
        else -> "${(dist * 10).roundToInt().toDouble() / 10}km"
    }

    override fun toString(): String {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE)
    }
}