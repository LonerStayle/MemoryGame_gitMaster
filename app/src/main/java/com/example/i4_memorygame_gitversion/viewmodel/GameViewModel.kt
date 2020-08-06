package com.example.i4_memorygame_gitversion.viewmodel


import android.content.Context
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import com.example.i4_memorygame_gitversion.R
import com.example.i4_memorygame_gitversion.databinding.FragmentGameBinding
import com.example.i4_memorygame_gitversion.db.dao.Dao
import com.example.i4_memorygame_gitversion.db.entity.GameModel
import com.example.i4_memorygame_gitversion.view.activity.MainActivity
import com.example.i4_memorygame_gitversion.view.adapter.GameAdapter
import com.example.i4_memorygame_gitversion.view.contents.Contents
import kotlinx.coroutines.*

class GameViewModel(private val dao: Dao) : ViewModel() {

    private val uiScope = CoroutineScope(Dispatchers.Main + Job())
    val gameModelList: LiveData<List<GameModel>>
        get() = dao.getAllList()

    fun insert(round: Int, row: Int, col: Int) {
        uiScope.launch {

            withContext(Dispatchers.IO) {
                dao.insert(GameModel(round = round, row = row, col = col))
            }
        }

    }

    fun timeBarAnimation(binding: FragmentGameBinding, dp: Float) {

        binding.viewTimeBar.animate().apply {
            translationXBy(0f)
            translationX(-MainActivity.displayWidth / 1.92f * dp)
            interpolator = LinearInterpolator()
            duration = Contents.GAME_ROUND_TIME
        }.start()
    }

    fun timeBarAnimationCancel(binding: FragmentGameBinding) {

        binding.apply {
            viewTimeBar.animate().cancel()
            viewTimeBar.animate().translationX(0f).duration = 10

        }
    }

    fun answerIsWrong(
        binding: FragmentGameBinding, context: Context, previewCorrectAnswer: () -> Unit,
        gameInProgress: () -> Unit
    ) {

        binding.apply {
            timeBarAnimationCancel(binding)

            textViewInfo.text = context.resources.getString(R.string.tv_wrongAnswer)
            imageViewImageCheck.visibility = View.GONE
            linearLayoutInfo.background =
                context.resources.getDrawable(R.drawable.wrong_answer_background, null)

            CoroutineScope(Dispatchers.Main).launch {
                delay(2000)
                (recyclerView.adapter as GameAdapter).run {
                    gameSettingPosition = Contents.GAME_READY_MODE
                    linearLayoutInfo.background =
                        context.resources.getDrawable(R.drawable.info_background, null)
                    textViewInfo.text = context.resources.getString(R.string.tv_gameReady)
                    notifyDataSetChanged()

                    delay(2000)
                    previewCorrectAnswer()
                    delay(4000)
                    gameInProgress()
                }
            }
        }
    }

    fun goToTheNextRound(
        binding: FragmentGameBinding, context: Context, previewCorrectAnswer: () -> Unit,
        gameInProgress: () -> Unit
    ) {
        binding.apply {
            timeBarAnimationCancel(binding)
            Contents.startRound++

            currentRound = Contents.startRound
            imageViewImageCheck.visibility = View.GONE
            linearLayoutInfo.background =
                context.resources.getDrawable(R.drawable.answer_background, null)
            textViewInfo.text = context.resources.getString(R.string.tv_answer)

            CoroutineScope(Dispatchers.Main).launch {
                delay(2000)

                (recyclerView.adapter as GameAdapter).run {
                    gameSettingPosition = Contents.GAME_READY_MODE
                    linearLayoutInfo.background =
                        context.resources.getDrawable(R.drawable.info_background, null)
                    textViewInfo.text = context.resources.getString(R.string.tv_gameReady)
                    notifyDataSetChanged()

                    delay(2000)
                    previewCorrectAnswer()

                    delay(4000)
                    gameInProgress()

                }

            }
        }
    }

    fun afterTheLastGameEvent(fragment: Fragment, finalRound: Int, row: Int, col: Int) {
        fragment.findNavController().navigate(R.id.action_gameFragment_to_recordFragment)

        insert(finalRound, row, col)
    }
}