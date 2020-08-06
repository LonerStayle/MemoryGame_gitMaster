package com.example.i4_memorygame_gitversion.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.i4_memorygame_gitversion.db.entity.GameModel
import androidx.lifecycle.LiveData

@Dao
interface Dao {

    @Query("SELECT*FROM GameModel")
    fun getAllList():LiveData<List<GameModel>>

    @Insert
    fun insert(gameModel: GameModel)

}