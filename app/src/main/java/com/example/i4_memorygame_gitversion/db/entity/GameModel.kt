package com.example.i4_memorygame_gitversion.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GameModel(
    @PrimaryKey(autoGenerate = true)
    val id:Long= 0L,
    val round:Int,
    val row:Int,
    val col:Int
)