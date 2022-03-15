package com.example.datepicker.adapter.view_holders

import android.view.View
import com.example.datepicker.MonthView
import com.example.datepicker.adapter.DiffVH
import com.example.datepicker.adapter.MonthAdapter
import com.example.datepicker.adapter.data_holders.MonthDH

class MonthVH(containerView: View, listener: MonthAdapter.IClickListener?) : DiffVH<MonthDH>(containerView) {

    init {
        itemView.setOnClickListener {
            listener?.onMonthClicked(data.month)
        }
    }

    override fun render(data: MonthDH) {
        renderView(data)
    }

    override fun render(data: MonthDH, payloads: Set<String>) {
        renderView(data)
    }

    private fun renderView(data: MonthDH) {
        val monthView = itemView as MonthView

        monthView.init(data.month, data.cells, data.displayOnly,
            data.dateTypeface, data.deactivatedDates)

    }
}