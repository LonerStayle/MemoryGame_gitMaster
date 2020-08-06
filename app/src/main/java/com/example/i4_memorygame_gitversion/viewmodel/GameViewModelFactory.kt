package com.example.i4_memorygame_gitversion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.i4_memorygame_gitversion.db.dao.Dao

class GameViewModelFactory(private val dao:Dao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            @Suppress("Unchecked_cast")
            return GameViewModel(dao) as T
        }
     throw IllegalAccessException("UnKnown ViewModel Class")
    }

}