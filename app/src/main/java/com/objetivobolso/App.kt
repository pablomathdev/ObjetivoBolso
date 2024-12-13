package com.objetivobolso

import android.app.Application
import com.objetivobolso.data.AppDatabase

class App:Application() {

    lateinit var database: AppDatabase
    override fun onCreate() {
        super.onCreate()
        database = AppDatabase.getDatabase(this)
    }
}