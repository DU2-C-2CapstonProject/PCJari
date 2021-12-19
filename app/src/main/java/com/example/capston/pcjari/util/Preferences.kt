package com.example.capston.pcjari.util

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.capston.pcjari.BuildConfig

object Preferences {
    private lateinit var preferences: SharedPreferences

    object Key {
        const val FIRST_SCREEN_INDEX = "FIRST_SCREEN_INDEX"
        const val GPS_DISTANCE = "GPS_DISTANCE"
        const val FAVORITE_LIST = "FAVORITE_LIST"
    }

    fun init(context: Context) {
        preferences = context.getSharedPreferences(BuildConfig.APPLICATION_ID, Activity.MODE_PRIVATE)
    }

    var first_screen_index: Int
        get() = preferences.getInt(Key.FIRST_SCREEN_INDEX, 0)
        set(value) = preferences.edit { putInt(Key.FIRST_SCREEN_INDEX, value) }

    var gps_distance: Int
        get() = preferences.getInt(Key.GPS_DISTANCE, 0)
        set(value) = preferences.edit { putInt(Key.GPS_DISTANCE, value) }

    private var favorite_list: MutableSet<String>? = null
    fun getFavoriteList(): MutableSet<String> {
        if(favorite_list == null)
            favorite_list = preferences.getStringSet(Key.FAVORITE_LIST, HashSet()) ?: HashSet()

        return favorite_list as MutableSet<String>
    }

    fun addFavorite(index: Int) {
        favorite_list?.run {
            add(index.toString())
            preferences.edit { putStringSet(Key.FAVORITE_LIST, favorite_list) }
        }
    }
    fun removeFavorite(index: Int) {
        favorite_list?.run {
            remove(index.toString())
            preferences.edit { putStringSet(Key.FAVORITE_LIST, favorite_list) }
        }
    }
}