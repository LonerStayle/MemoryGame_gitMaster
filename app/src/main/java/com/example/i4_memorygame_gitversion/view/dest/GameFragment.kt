package com.example.i4_memorygame_gitversion.view.dest

import android.view.View

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.i4_memorygame_gitversion.R
import com.example.i4_memorygame_gitversion.databinding.FragmentGameBinding
import com.example.i4_memorygame_gitversion.db.GameDatabase
import com.example.i4_memorygame_gitversion.view.activity.MainActivity
import com.example.i4_memorygame_gitversion.view.adapter.GameAdapter
import com.example.i4_memorygame_gitversion.view.contents.Contents
import com.example.i4_memorygame_gitversion.view.viewbase.FragmentBase
import com.example.i4_memorygame_gitversion.viewmodel.GameViewModel
import com.example.i4_memorygame_gitversion.viewmodel.GameViewModelFactory
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class GameFragment : FragmentBase<FragmentGameBinding>(R.layout.fragment_game) {


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
        currentRound = Contents.startRound
        maxRound = finalRound

        setRecyclerView()

        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)
            previewCorrectAnswer()

            delay(4000)
            gameInProgress()
        }
    }


    private fun FragmentGameBinding.setRecyclerView() {

        recyclerView.adapter =
            GameAdapter(
                gameSettingPosition = Contents.GAME_READY_MODE,
                row = row,
                col = col,
                maxRound = finalRound,
                clickEvnet_timeBarAniReStart =
                {
                    viewModel.timeBarAnimationCancel(binding)
                    viewModel.timeBarAnimation(binding, dp)
                },
                clickEvnet_answerIsWrongInClickEvent =
                {
                    viewModel.answerIsWrong(
                        binding,
                        requireContext(),
                        { previewCorrectAnswer() },
                        { gameInProgress() })
                },
                clickEvnet_goToTheNextRound = {
                    viewModel.goToTheNextRound(
                        binding,
                        requireContext(),
                        { previewCorrectAnswer() },
                        { gameInProgress() }
                    )
                },
                clickEvnet_afterTheLastGameEvent = {
                    viewModel.afterTheLastGameEvent(
                        this@GameFragment,
                        finalRound, row, col
                    )
                }
            )

        recyclerView.layoutManager = GridLayoutManager(requireContext(), row)

    }



    private fun previewCorrectAnswer() {

        binding.textViewInfo.text = resources.getString(R.string.tv_remember)
        viewModel.timeBarAnimation(binding,dp)

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

}
