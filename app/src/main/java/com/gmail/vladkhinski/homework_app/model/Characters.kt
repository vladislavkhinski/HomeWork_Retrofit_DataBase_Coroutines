package com.gmail.vladkhinski.homework_app.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Characters(
    @SerializedName("char_id")
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "img_path")
    val img: String,
    @ColumnInfo(name = "birthday")
    val birthday: String,
    @ColumnInfo(name = "nickname")
    val nickname: String,
    @ColumnInfo(name = "portrayed")
    val portrayed: String,
)