package com.example.i4_memorygame_gitversion.viewmodel

import androidx.lifecycle.ViewModel
import com.example.i4_memorygame_gitversion.db.dao.Dao
import androidx.lifecycle.LiveData
import com.example.i4_memorygame_gitversion.db.entity.GameModel
import kotlinx.coroutines.*

class GameViewModel(private val dao: Dao):ViewModel() {

    private val coMain = CoroutineScope(Dispatchers.Main + Job())
    val gameModelList: LiveData<List<GameModel>>
    get() = dao.getAllList()

    fun insert(round:Int,row:Int,col:Int){
        coMain.launch {
            dao.insert(GameModel(round= round,row = row,col = col))
        }

    }
//    fun insert(gameModel: GameModel){
//        coMain.launch {
//            dao.insert(gameModel)
//        }
//
//    }
}