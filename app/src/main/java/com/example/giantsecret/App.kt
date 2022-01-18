package com.example.giantsecret

import android.app.Application

class App : Application() {
    companion object {
        lateinit var prefs: InformationSharedPreferences
    }

    override fun onCreate() {
        prefs = InformationSharedPreferences(applicationContext)
        super.onCreate()
    }
}