package com.example.i4_memorygame_gitversion.view.dest

import com.example.i4_memorygame_gitversion.R
import com.example.i4_memorygame_gitversion.databinding.FragmentRecordBinding
import com.example.i4_memorygame_gitversion.view.viewbase.FragmentBase
import androidx.lifecycle.Observer
import com.example.i4_memorygame_gitversion.view.adapter.RecordAdapter

class RecordFragment : FragmentBase<FragmentRecordBinding>(R.layout.fragment_record) {

    private val args by lazy { RecordFragmentArgs.fromBundle(requireArguments()) }

    override fun FragmentRecordBinding.setOnCreateViewEvent() {
        recyclerView.adapter = RecordAdapter()
        viewModel.gameModelList.observe(viewLifecycleOwner, Observer {
            (recyclerView.adapter as RecordAdapter).run {
                gameModelList = it
            }
        })
    }
}