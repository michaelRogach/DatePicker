package com.example.datepicker.adapter.diff_callbacks

import androidx.recyclerview.widget.DiffUtil
import com.example.datepicker.adapter.data_holders.MonthDH
import com.example.datepicker.adapter.equalObjects

class MonthDiffCallback : DiffUtil.ItemCallback<MonthDH>() {

    override fun areItemsTheSame(oldItem: MonthDH, newItem: MonthDH): Boolean {
        return equalObjects(oldItem.month.month, newItem.month.month) &&
                equalObjects(oldItem.month.year, newItem.month.year)
    }

    override fun areContentsTheSame(oldItem: MonthDH, newItem: MonthDH): Boolean {
        return equalObjects(oldItem.month, newItem.month) &&
                equalObjects(oldItem.cells, newItem.cells) &&
                equalObjects(oldItem.displayOnly, newItem.displayOnly) &&
                equalObjects(oldItem.titleTypeface, newItem.titleTypeface) &&
                equalObjects(oldItem.dateTypeface, newItem.dateTypeface) &&
                equalObjects(oldItem.deactivatedDates, newItem.deactivatedDates)
    }

    override fun getChangePayload(oldItem: MonthDH, newItem: MonthDH): MutableSet<String> {
        return mutableSetOf<String>()
    }
}