package com.example.capston.pcjari.util

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.capston.pcjari.BuildConfig
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Preferences {
    private lateinit var preferences: SharedPreferences

    object Key {
        const val FIRST_SCREEN_INDEX = "FIRST_SCREEN_INDEX"
        const val GPS_DISTANCE = "GPS_DISTANCE"
        const val FAVORITE_LIST = "FAVORITE_LIST"
    }

    fun init(context: Context) {
        preferences = context.getSharedPreferences(BuildConfig.APPLICATION_ID, Activity.MODE_PRIVATE)
        favorite_list = Gson().fromJson(preferences.getString(Key.FAVORITE_LIST, ""), object: TypeToken<ArrayList<Int>>() {}.type) ?: ArrayList()
    }

    var first_screen_index: Int
        get() = preferences.getInt(Key.FIRST_SCREEN_INDEX, 0)
        set(value) = preferences.edit { putInt(Key.FIRST_SCREEN_INDEX, value) }

    var gps_distance: Int
        get() = preferences.getInt(Key.GPS_DISTANCE, 0)
        set(value) = preferences.edit { putInt(Key.GPS_DISTANCE, value) }

    lateinit var favorite_list: ArrayList<Int>

    fun addFavorite(index: Int) {
        favorite_list.add(index)
        preferences.edit { putString(Key.FAVORITE_LIST, Gson().toJson(favorite_list)) }
    }
    fun removeFavorite(index: Int) {
        favorite_list.remove(index)
        preferences.edit { putString(Key.FAVORITE_LIST, Gson().toJson(favorite_list)) }
    }
}