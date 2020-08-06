package com.example.i4_memorygame_gitversion.view.dest

import android.view.View
import android.view.animation.LinearInterpolator
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.i4_memorygame_gitversion.R
import com.example.i4_memorygame_gitversion.databinding.FragmentGameBinding
import com.example.i4_memorygame_gitversion.view.activity.MainActivity
import com.example.i4_memorygame_gitversion.view.adapter.GameAdapter
import com.example.i4_memorygame_gitversion.view.contents.Contents
import com.example.i4_memorygame_gitversion.view.viewbase.FragmentBase
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class GameFragment : FragmentBase<FragmentGameBinding>(R.layout.fragment_game) {


    companion object {
        var startRound = 1
    }

    private val args by lazy {
        GameFragmentArgs.fromBundle(
            requireArguments()
        )
    }
    private val dp by lazy { resources.displayMetrics.density }

    private val finalRound by lazy{args.maxRound}
    private val row by lazy{args.row}
    private val col by lazy{args.col}

    override fun FragmentGameBinding.setOnCreateViewEvent() {
        currentRound = startRound
        maxRound = finalRound

        setRecyclerView()

        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)
            previewCorrectAnswer()

            delay(4000)
            gameInProgress()
        }
    }


    private fun setRecyclerView() {

        binding.apply {
            recyclerView.adapter =
                GameAdapter(
                    gameSettingPosition = Contents.GAME_READY_MODE,
                    row = row,
                    col = col,
                    maxRound = finalRound,
                    clickEvnet_timeBarAniReStart = { timeBarAnimationCancel();timeBarAnimation() },
                    clickEvnet_answerIsWrongInClickEvent = { answerIsWrong() },
                    clickEvnet_goToTheNextRound = { goToTheNextRound() },
                    clickEvnet_afterTheLastGameEvent = { afterTheLastGameEvent() }

                )

            recyclerView.layoutManager = GridLayoutManager(requireContext(), row)
        }

    }


    private fun previewCorrectAnswer() {

        binding.textViewInfo.text = resources.getString(R.string.tv_remember)
        timeBarAnimation()

        (recyclerView.adapter as GameAdapter).run {
            gameSettingPosition = Contents.GAME_REMEMBER_MODE
            notifyDataSetChanged()
        }
    }

    private fun gameInProgress() {
        binding.apply {

            textViewInfo.text = resources.getString(R.string.tv_find)
            imageViewImageCheck.visibility = View.VISIBLE
            imageViewImageCheck.setBackgroundResource(R.drawable.select_image_result)

            (recyclerView.adapter as GameAdapter).run {
                gameSettingPosition = Contents.GAME_START_MODE
                notifyDataSetChanged()
            }
        }
    }

    private fun timeBarAnimation() {

        binding.viewTimeBar.animate().apply {
            translationXBy(0f)
            translationX(-MainActivity.displayWidth / 1.92f * dp)
            interpolator = LinearInterpolator()
            duration = Contents.GAME_ROUND_TIME
        }.start()
    }

    private fun timeBarAnimationCancel() {

        binding.apply {
            viewTimeBar.animate().cancel()
            viewTimeBar.animate().translationX(0f).duration = 10

        }

    }


    private fun answerIsWrong() {

        binding.apply {
            timeBarAnimationCancel()

            textViewInfo.text = resources.getString(R.string.tv_wrongAnswer)
            imageViewImageCheck.visibility = View.GONE
            linearLayoutInfo.background =
                resources.getDrawable(R.drawable.wrong_answer_background, null)

            CoroutineScope(Dispatchers.Main).launch {
                delay(2000)
                (recyclerView.adapter as GameAdapter).run {
                    gameSettingPosition = Contents.GAME_READY_MODE
                    linearLayoutInfo.background =
                        resources.getDrawable(R.drawable.info_background, null)
                    textViewInfo.text = resources.getString(R.string.tv_gameReady)
                    notifyDataSetChanged()

                    delay(2000)
                    previewCorrectAnswer()

                    delay(4000)
                    gameInProgress()
                }
            }
        }

    }

    private fun goToTheNextRound() {
        binding.apply {
            timeBarAnimationCancel()
            startRound++

            currentRound = startRound
            imageViewImageCheck.visibility = View.GONE
            linearLayoutInfo.background = resources.getDrawable(R.drawable.answer_background, null)
            textViewInfo.text = resources.getString(R.string.tv_answer)

            CoroutineScope(Dispatchers.Main).launch {
                delay(2000)

                (recyclerView.adapter as GameAdapter).run {
                    gameSettingPosition = Contents.GAME_READY_MODE
                    linearLayoutInfo.background =
                        resources.getDrawable(R.drawable.info_background, null)
                    textViewInfo.text = resources.getString(R.string.tv_gameReady)
                    notifyDataSetChanged()

                    delay(2000)
                    previewCorrectAnswer()

                    delay(4000)
                    gameInProgress()

                }

            }
        }
    }

    private fun afterTheLastGameEvent() {
        findNavController().navigate(R.id.action_gameFragment_to_recordFragment)

//        val gameModel = GameModel(
//            round = args.maxRound,
//            col = args.col,
//            row = args.row
//        )
//        viewModel.insert(gameModel)
        viewModel.insert(finalRound,
            row,
            col)

    }

}
