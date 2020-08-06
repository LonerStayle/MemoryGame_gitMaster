package com.example.i4_memorygame_gitversion.view.viewbase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.i4_memorygame_gitversion.db.GameDatabase
import com.example.i4_memorygame_gitversion.viewmodel.GameViewModel
import com.example.i4_memorygame_gitversion.viewmodel.GameViewModelFactory
import androidx.lifecycle.ViewModelProvider

abstract class FragmentBase<VDB : ViewDataBinding>(@LayoutRes private val layoutRes: Int) :
    Fragment() {



    protected lateinit var binding: VDB

    protected abstract fun VDB.setOnCreateViewEvent()
     private val database by lazy { GameDatabase.getInstance(requireContext()) }
    private val viewModelInFactory by lazy { GameViewModelFactory(database.dao) }

//   protected val viewModel by viewModels<GameViewModel> { viewModelInFactory }
    protected val viewModel by lazy{
    ViewModelProvider(this,viewModelInFactory).get(GameViewModel::class.java)
}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = DataBindingUtil.inflate<VDB>(inflater, layoutRes, container, false).run {

        lifecycleOwner = this@FragmentBase
        binding = this

        setOnCreateViewEvent()

        root
    }
}