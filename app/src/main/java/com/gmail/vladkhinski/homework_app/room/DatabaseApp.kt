package com.gmail.vladkhinski.homework_app.room

import android.app.Application
import android.content.Context
import androidx.room.Room

class MyApp : Application() {

    private var _appDatabase: AppDatabase? = null
    val appDatabase get() = requireNotNull(_appDatabase)

    override fun onCreate() {
        super.onCreate()
        _appDatabase = Room
            .databaseBuilder(
                this,
                AppDatabase::class.java,
                "app_database.db"
            ).build()
    }
}

val Context.appDatabase: AppDatabase
    get() = when (this) {
        is MyApp -> appDatabase
        else -> applicationContext.appDatabase
    }