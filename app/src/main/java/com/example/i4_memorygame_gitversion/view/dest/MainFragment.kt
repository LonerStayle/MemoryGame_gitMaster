package com.example.i4_memorygame_gitversion.view.dest

import androidx.navigation.fragment.findNavController
import com.example.i4_memorygame_gitversion.R
import com.example.i4_memorygame_gitversion.databinding.FragmentMainBinding
import com.example.i4_memorygame_gitversion.view.viewbase.FragmentBase

class MainFragment:FragmentBase<FragmentMainBinding>(R.layout.fragment_main) {
    override fun FragmentMainBinding.setOnCreateViewEvent() {
        buttonGoToTheGameStart.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_gameSettingFragment)
        }
        buttonGoToTheGameRecord.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_recordFragment)
        }
    }
}