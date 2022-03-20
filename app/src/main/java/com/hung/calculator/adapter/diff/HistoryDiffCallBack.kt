package com.hung.calculator.adapter.diff

import androidx.recyclerview.widget.DiffUtil
import com.hung.calculator.model.History

class HistoryDiffCallBack(
    private val oldItems: List<History>,
    private val newItems: List<History>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
       return oldItems[oldItemPosition].id == newItems[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition] == newItems[newItemPosition]
    }
}