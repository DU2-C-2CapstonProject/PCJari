package com.example.capston.pcjari

import android.app.Application
import com.example.capston.pcjari.util.Preferences

class PCJariApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Preferences.init(this)
    }

    fun context() = applicationContext
}