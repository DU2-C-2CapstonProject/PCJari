package com.example.capston.pcjari.util.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    const val serverUrl = "https://sosocom.duckdns.org/php/"

    private var instance : RetrofitNetwork? = null

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