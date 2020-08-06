package com.example.i4_memorygame_gitversion.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.i4_memorygame_gitversion.db.dao.Dao
import com.example.i4_memorygame_gitversion.db.entity.GameModel

@Database(entities = [GameModel::class],version = 1,exportSchema = false)

abstract class GameDatabase: RoomDatabase() {

    abstract val dao: Dao

    companion object {
        @Volatile
        private var INSTANCE: GameDatabase? = null
        fun getInstance(context: Context): GameDatabase = synchronized(this)  {
            INSTANCE ?: Room.databaseBuilder(
                context,
                GameDatabase::class.java,
                "game_database"
            ).fallbackToDestructiveMigration()
                .build().also {
                    INSTANCE = it
                }

        }
    }
}