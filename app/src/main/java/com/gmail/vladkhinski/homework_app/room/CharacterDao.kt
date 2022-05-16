package com.gmail.vladkhinski.homework_app.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gmail.vladkhinski.homework_app.model.Characters

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<Characters>)

    @Query("SELECT * FROM characters")
    suspend fun getAllCharacters(): List<Characters>

}