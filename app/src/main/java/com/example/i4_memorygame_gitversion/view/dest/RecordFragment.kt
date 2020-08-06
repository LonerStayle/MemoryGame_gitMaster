package com.example.i4_memorygame_gitversion.view.dest

import androidx.fragment.app.viewModels
import com.example.i4_memorygame_gitversion.R
import com.example.i4_memorygame_gitversion.databinding.FragmentRecordBinding
import com.example.i4_memorygame_gitversion.view.viewbase.FragmentBase
import androidx.lifecycle.Observer
import com.example.i4_memorygame_gitversion.db.GameDatabase
import com.example.i4_memorygame_gitversion.view.adapter.RecordAdapter
import com.example.i4_memorygame_gitversion.viewmodel.GameViewModel
import com.example.i4_memorygame_gitversion.viewmodel.GameViewModelFactory
import androidx.lifecycle.ViewModelProvider

class RecordFragment : FragmentBase<FragmentRecordBinding>(R.layout.fragment_record) {


    private val args by lazy { RecordFragmentArgs.fromBundle(requireArguments()) }

    override fun FragmentRecordBinding.setOnCreateViewEvent() {
        recyclerView.adapter = RecordAdapter()
        viewModel.gameModelList.observe(viewLifecycleOwner, Observer {
            (recyclerView.adapter as RecordAdapter).run {
                gameModelList = it
                notifyDataSetChanged()
            }
        })
    }
}