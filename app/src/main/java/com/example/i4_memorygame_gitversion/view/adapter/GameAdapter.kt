package com.example.i4_memorygame_gitversion.view.adapter

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.i4_memorygame_gitversion.R
import com.example.i4_memorygame_gitversion.databinding.ViewholderGameBinding
import com.example.i4_memorygame_gitversion.view.contents.Contents
import com.example.i4_memorygame_gitversion.view.dest.GameFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class GameAdapter(
    var gameSettingPosition: Int,
    var clickEvnet_timeBarAniReStart: () -> Unit,
    var clickEvnet_answerIsWrongInClickEvent: () -> Unit,
    var clickEvnet_goToTheNextRound: () -> Unit,
    var clickEvnet_afterTheLastGameEvent: () -> Unit,
    var maxRound: Int,
    var row: Int,
    var col: Int
) : RecyclerView.Adapter<GameAdapter.ViewHolder>() {

    private var checkAnswerList = mutableListOf<Int>()
    private var answerList = mutableListOf<Int>()
    private var answerClickList = mutableListOf<Int>()
    private val gameBoardSetting = row * col


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.viewholder_game, parent, false)
        )

    override fun getItemCount(): Int = gameBoardSetting

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (row >= 5)
            holder.setWidthAndHeight(50f)
        else
            holder.setWidthAndHeight(80f)

        when {
            (gameSettingPosition == Contents.GAME_READY_MODE) -> {
                holder.setAnswerList()
                holder.itemView.setOnClickListener {
                    return@setOnClickListener
                }
            }

            (gameSettingPosition == Contents.GAME_REMEMBER_MODE) -> {
                holder.answerChecking()
                holder.itemView.setOnClickListener {
                    return@setOnClickListener
                }
            }

            (gameSettingPosition == Contents.GAME_START_MODE) -> {
                holder.beforeConfirmCorrectAnswerBind()
                holder.itemView.setOnClickListener {
                    holder.itemClickEvent()
                }
            }

        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = DataBindingUtil.bind<ViewholderGameBinding>(view)

        private fun confirmCorrectAnswerBind() {
            binding?.imageViewItem?.setBackgroundResource(R.drawable.select_image_result)

        }

        fun beforeConfirmCorrectAnswerBind() {
            binding?.imageViewItem?.setBackgroundResource(R.drawable.select_image_default)
        }

        private fun wrongAnswerBind() {
            binding?.imageViewItem?.setBackgroundResource(R.drawable.wrong_answer_background)
        }


        fun setAnswerList() {
            beforeConfirmCorrectAnswerBind()

            answerList.clear()
            answerClickList.clear()
            checkAnswerList.clear()

            for (i in 0 until gameBoardSetting - 1) {
                checkAnswerList.add(i)
            }

            val shuffleMode = checkAnswerList.shuffled()

            answerList = mutableListOf(
                shuffleMode[0],
                shuffleMode[1],
                shuffleMode[2]
            )
        }

        fun answerChecking() {
            beforeConfirmCorrectAnswerBind()

            when (adapterPosition) {

                answerList[0] -> {
                        confirmCorrectAnswerBind()
                }
                answerList[1] -> {
                    CoroutineScope(Dispatchers.Main).launch {
                        delay(1500)
                        confirmCorrectAnswerBind()
                    }

                }
                answerList[2] -> {
                    CoroutineScope(Dispatchers.Main).launch {
                        delay(3000)
                        confirmCorrectAnswerBind()

                    }
                }
            }
        }

        fun itemClickEvent() {


            if (answerClickList.size >= 3 || answerClickList.contains(adapterPosition))
                return

            confirmCorrectAnswerBind()
            answerClickList.add(adapterPosition)
            clickEvnet_timeBarAniReStart()

            when {
                answerClickList.size >= 4 ||
                        !answerList.contains(answerClickList.last()) -> {
                    wrongAnswerBind()
                    /**
                     *fixme: 클릭리스너 막기 임시 방편용 disable 하는법 연구후 변경예졍임
                     */
                    answerClickList.addAll(answerList)
                    clickEvnet_answerIsWrongInClickEvent()


                }


                (Contents.startRound < maxRound) &&
                        (answerList.size == answerClickList.size) &&
                        (answerList.containsAll(answerClickList)) ->
                    clickEvnet_goToTheNextRound()



                (Contents.startRound == maxRound) &&
                        (answerList.size == answerClickList.size) &&
                        (answerList.containsAll(answerClickList)) ->
                    clickEvnet_afterTheLastGameEvent()

            }
        }

        fun setWidthAndHeight(value: Float) {

            binding?.viewHolderLayout?.apply {
                val lp = layoutParams
                val setDp = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    value, context.resources.displayMetrics
                ).toInt()

                lp.width = setDp
                lp.height = setDp

                layoutParams.width = lp.width
                layoutParams.height = lp.height
            }

        }
    }


}



