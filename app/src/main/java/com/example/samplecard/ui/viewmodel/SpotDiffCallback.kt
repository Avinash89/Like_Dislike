package com.example.samplecard.ui.viewmodel

import androidx.recyclerview.widget.DiffUtil
import com.example.samplecard.model.Result

class SpotDiffCallback(
    private val old: List<Result>,
    private val new: List<Result>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return old.size
    }

    override fun getNewListSize(): Int {
        return new.size
    }

    override fun areItemsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return old[oldPosition].id == new[newPosition].id
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return old[oldPosition] == new[newPosition]
    }

}
