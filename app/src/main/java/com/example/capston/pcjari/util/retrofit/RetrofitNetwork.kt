package com.example.capston.pcjari.util.retrofit

import com.example.capston.pcjari.activity.a111_location.location.LocationListResponse
import com.example.capston.pcjari.activity.a210_seat.seat.SeatResponse
import com.example.capston.pcjari.pc.PCListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitNetwork {
    @GET("pclist_search.php")
    fun getPCListByAddress(
            @Query("code") code : Int,
            @Query("gu") gu : String,
            @Query("dong") dong : String,
            @Query("name") name : String?
    ) : Call<PCListResponse>

    @GET("pclist_search.php")
    fun getPCListByGps(
            @Query("code") code : Int,
            @Query("lat") lat : Double,
            @Query("lng") lng : Double,
            @Query("dist") dist : String,
            @Query("name") name : String?
    ) : Call<PCListResponse>

    @GET("pclist_search.php")
    fun getPCListByFavorite(
            @Query("code") code : Int,
            @Query("favorite") favorite : String,
            @Query("name") name : String?
    ) : Call<PCListResponse>

    @GET("jusosearch.php")
    fun getLocationList(
            @Query("dong") dong : String
    ) : Call<LocationListResponse>

    @GET("seat_search.php")
    fun getSeatList(
            @Query("id") id : Int
    ) : Call<SeatResponse>
}