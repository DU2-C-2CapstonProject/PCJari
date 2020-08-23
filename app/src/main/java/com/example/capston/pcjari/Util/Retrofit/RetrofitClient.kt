package com.example.capston.pcjari.Util.Retrofit

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val serverUrl = "http://sosocom.iptime.org:80/php/"

    private var instance : RetrofitNetwork? = null
    private val gson = GsonBuilder().setLenient().create()

    fun getInstance() : RetrofitNetwork {
        if(instance == null) {
            instance = Retrofit.Builder()
                    .baseUrl(serverUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(RetrofitNetwork :: class.java)
        }

        return instance!!
    }
}