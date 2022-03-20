package com.hung.calculator.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hung.calculator.adapter.diff.HistoryDiffCallBack
import com.hung.calculator.databinding.ItemHistoryBinding
import com.hung.calculator.model.History

class HistoryAdapter:
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    private val listHistory= arrayListOf<History>()
    fun submit(items: List<History>){
        val diffCallBack = HistoryDiffCallBack(listHistory,items)
        val diff = DiffUtil.calculateDiff(diffCallBack)
        this.listHistory.clear()
        this.listHistory.addAll(items)
        diff.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listHistory[position])
    }

    override fun getItemCount() = listHistory.size

    class ViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(history: History) {
            binding.tvExp.text = history.exp
            binding.tvAnswer.text = history.result
        }
    }
}


