package com.example.datepicker.adapter

import android.view.View
import com.example.datepicker.MonthDescriptor
import com.example.datepicker.MonthView
import com.example.datepicker.R
import com.example.datepicker.adapter.data_holders.HeaderDH
import com.example.datepicker.adapter.data_holders.MonthDH
import com.example.datepicker.adapter.diff_callbacks.MonthsCallback
import com.example.datepicker.adapter.view_holders.HeaderVH
import com.example.datepicker.adapter.view_holders.MonthVH

class MonthAdapter(private val styleData: MonthView.StyleData, private val listener: IClickListener? = null) : DiffAdapter<DiffVH<Any>, Any>(MonthsCallback()) {

    companion object {
        const val TYPE_SECTION_HEADER = 1
        const val TYPE_MONTH = 2
    }

    override fun getLayoutResourceId(viewType: Int): Int = when (viewType) {
        TYPE_SECTION_HEADER -> R.layout.item_header_date
        TYPE_MONTH -> R.layout.month
        else -> throw RuntimeException("This item type not supported by this adapter!")
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is HeaderDH -> TYPE_SECTION_HEADER
            is MonthDH -> TYPE_MONTH
            else -> throw RuntimeException("This item type not supported by this adapter!")
        }
    }

    override fun createViewHolder(rootView: View, viewType: Int): DiffVH<Any> {
        return when (viewType) {
            TYPE_SECTION_HEADER -> HeaderVH(rootView)
            TYPE_MONTH -> {
                val monthView = rootView as MonthView
                monthView.apply {
                    setDayViewAdapter(styleData.adapter)
                    setUpView(styleData)
                }
                MonthVH(rootView, listener)
            }
            else -> throw Exception("This item type not supported by this adapter!")
        } as DiffVH<Any>
    }

    interface IClickListener {
        fun onMonthClicked(position: Int, month: MonthDescriptor)
    }
}