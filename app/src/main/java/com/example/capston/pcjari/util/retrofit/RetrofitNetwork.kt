package com.example.capston.pcjari.util.retrofit

import com.example.capston.pcjari.activity.a111_address.address.AddressListItem
import com.example.capston.pcjari.activity.a210_seat.seat.Seat
import com.example.capston.pcjari.pc.PCListItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitNetwork {
    @GET("pclist_search.php")
    fun getPCListByAddress(
            @Query("code") code : Int = 0,
            @Query("gu") gu : String,
            @Query("dong") dong : String,
            @Query("name") name : String?
    ) : Call<ArrayList<PCListItem>>

    @GET("pclist_search.php")
    fun getPCListByGps(
        @Query("code") code: Int = 1,
        @Query("lat") lat: Double,
        @Query("lng") lng: Double,
        @Query("dist") dist: Double,
        @Query("name") name: String?
    ) : Call<ArrayList<PCListItem>>

    @GET("pclist_search.php")
    fun getPCListByFavorite(
            @Query("code") code : Int = 2,
            @Query("favorite") favorite : String,
            @Query("name") name : String?
    ) : Call<ArrayList<PCListItem>>

    @GET("juso_search.php")
    fun getLocationList(
            @Query("dong") dong : String
    ) : Call<ArrayList<AddressListItem>>

    @GET("seat_search.php")
    fun getSeatList(
            @Query("id") id : Int
    ) : Call<ArrayList<Seat>>
}