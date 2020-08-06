package com.example.i4_memorygame_gitversion.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.i4_memorygame_gitversion.db.entity.GameModel
import com.example.i4_memorygame_gitversion.R
import com.example.i4_memorygame_gitversion.databinding.ViewholderRecordBinding

class RecordAdapter(
    var gameModelList: List<GameModel> = listOf()
) : RecyclerView.Adapter<RecordAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = DataBindingUtil.bind<ViewholderRecordBinding>(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.viewholder_record, parent, false)
        )

    override fun getItemCount() = gameModelList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding?.apply {
            val gameResult =  gameModelList[holder.adapterPosition]

         gameModel = gameResult
        }
    }


}