package com.example.i4_memorygame_gitversion.view.dest

import com.example.i4_memorygame_gitversion.R
import com.example.i4_memorygame_gitversion.databinding.FragmentGameSettingBinding
import com.example.i4_memorygame_gitversion.view.viewbase.FragmentBase

class GameSettingFragment :
    FragmentBase<FragmentGameSettingBinding>(R.layout.fragment_game_setting) {
    override fun FragmentGameSettingBinding.setOnCreateViewEvent() {

        buttonGameStart.setOnClickListener {

            findNavController().navigate(
                GameSettingFragmentDirections.actionGameSettingFragmentToGameFragment(
                    maxRound = numberPickerRound.value,
                    row = numberPickerRow.value,
                    col = numberPickerCol.value
                )
            )
        }
    }
}