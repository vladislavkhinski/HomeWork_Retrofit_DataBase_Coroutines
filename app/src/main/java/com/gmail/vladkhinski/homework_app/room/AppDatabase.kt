package com.gmail.vladkhinski.homework_app.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gmail.vladkhinski.homework_app.model.Characters

@Database(entities = [Characters::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun characterDao(): CharacterDao
}