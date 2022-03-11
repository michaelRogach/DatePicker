package com.example.datepicker.adapter.diff_callbacks

import androidx.recyclerview.widget.DiffUtil
import com.example.datepicker.adapter.data_holders.MonthDH

class MonthsCallback : DiffUtil.ItemCallback<Any>() {

    private val monthDiffCallback = MonthDiffCallback()

    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        return oldItem is MonthDH && newItem is MonthDH && monthDiffCallback.areItemsTheSame(oldItem, newItem)
    }

    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        return oldItem is MonthDH && newItem is MonthDH && monthDiffCallback.areContentsTheSame(oldItem, newItem)
    }

    override fun getChangePayload(oldItem: Any, newItem: Any): MutableSet<String> {
        return when {
            oldItem is MonthDH && newItem is MonthDH -> monthDiffCallback.getChangePayload(oldItem, newItem)
            else -> mutableSetOf()
        }
    }
}