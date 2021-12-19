package com.example.capston.pcjari.activity.a111_address.address

import com.google.gson.annotations.SerializedName
import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import java.io.Serializable

class AddressListItem() : Serializable {
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

    override fun toString(): String {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE)
    }
}