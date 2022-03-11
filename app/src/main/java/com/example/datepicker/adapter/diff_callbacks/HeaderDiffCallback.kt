package com.example.datepicker.adapter.diff_callbacks

import androidx.recyclerview.widget.DiffUtil
import com.example.datepicker.adapter.data_holders.HeaderDH
import com.example.datepicker.adapter.equalObjects
import com.example.datepicker.adapter.notEqualObjects

class HeaderDiffCallback : DiffUtil.ItemCallback<HeaderDH>() {

    companion object {
        const val DIFF_TITLE = "title"
    }

    override fun areItemsTheSame(oldItem: HeaderDH, newItem: HeaderDH): Boolean {
        return equalObjects(oldItem.dateLong, newItem.dateLong)
    }

    override fun areContentsTheSame(oldItem: HeaderDH, newItem: HeaderDH): Boolean {
        return equalObjects(oldItem.dateLong, newItem.dateLong) && equalObjects(oldItem.title, newItem.title)
    }

    override fun getChangePayload(oldItem: HeaderDH, newItem: HeaderDH): MutableSet<String> {
        val diffs = mutableSetOf<String>()

        if (notEqualObjects(oldItem.dateLong, newItem.dateLong))
            diffs.add(DIFF_TITLE)

        return diffs
    }
}