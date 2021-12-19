package com.example.capston.pcjari

import android.app.Application
import com.example.capston.pcjari.util.Preferences

class PCJariApplication : Application() {
    companion object {
        lateinit var instance: PCJariApplication
    }

    override fun onCreate() {
        super.onCreate()
        
        instance = this
        Preferences.init(this)
    }
}