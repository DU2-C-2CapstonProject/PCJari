package com.example.capston.pcjari.PC

import java.io.Serializable

/**
 * Created by KangSeungho on 2017-09-25.
 */
class PCListItem : Serializable {
    var pcID = 0
    var notice: String? = null
    var icon: String? = null
    var title: String? = null
    var si: String? = null
    var gu: String? = null
    var dong: String? = null
    var etc_juso: String? = null
    var tel: String? = null
    var cpu: String? = null
    var ram: String? = null
    var vga: String? = null
    var peripheral: String? = null
    var price = 0
    var isCard = false
    var seatLength = 0
    var location_x = 0.0
    var location_y = 0.0
    var totalSeat = 0
    var spaceSeat = 0
    var usingSeat = 0
    var dist = 0.0
}