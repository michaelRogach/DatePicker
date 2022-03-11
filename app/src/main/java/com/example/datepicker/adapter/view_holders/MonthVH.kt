package com.example.datepicker.adapter.view_holders

import android.view.View
import com.example.datepicker.MonthView
import com.example.datepicker.adapter.DiffVH
import com.example.datepicker.adapter.data_holders.MonthDH

class MonthVH(containerView: View) : DiffVH<MonthDH>(containerView) {


    override fun render(data: MonthDH) {
        renderView(data)
    }

    override fun render(data: MonthDH, payloads: Set<String>) {
        renderView(data)
//        payloads.forEach {
//            renderView(data)
//            when (it) {
//                CompletedJobsDiffCallback.DIFF_STORE_NAME -> renderStoreName()
//            }
//        }
    }

    private fun renderView(data: MonthDH) {
        val monthView = itemView as MonthView

        monthView.init(
            data.month, data.cells, data.displayOnly,
            data.titleTypeface, data.dateTypeface, data.deactivatedDates, arrayListOf())

    }
}