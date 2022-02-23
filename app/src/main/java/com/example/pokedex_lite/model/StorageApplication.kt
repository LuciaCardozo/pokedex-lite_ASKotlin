package com.example.pokedex_lite.model

import android.annotation.SuppressLint
import android.app.Application

class StorageApplication:Application() {
    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var prefs: Prefs
    }
    override fun onCreate() {
        super.onCreate()
        prefs = Prefs(applicationContext)
    }
}