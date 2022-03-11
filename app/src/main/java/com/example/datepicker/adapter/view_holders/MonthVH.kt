package com.example.datepicker.adapter.view_holders

import android.view.View
import com.example.datepicker.MonthView
import com.example.datepicker.adapter.DiffVH
import com.example.datepicker.adapter.data_holders.MonthDH
import java.util.*

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

        monthView.apply {
            setDividerColor(data.dividerColor)
            setDayTextColor(data.dayTextColorResId)
            setTitleTextColor(data.titleTextColor)
            setDisplayHeader(data.displayHeader)
            setHeaderTextColor(data.headerTextColor)
            if (data.dayBackgroundResId != 0) {
                setDayBackground(data.dayBackgroundResId)
            }
            isRtl = MonthView.isRtl(data.locale)
            this.locale = data.locale

            val originalDayOfWeek = data.today[Calendar.DAY_OF_WEEK]
            data.today[Calendar.DAY_OF_WEEK] = originalDayOfWeek
//            listener = data.listener
            setDecorators(data.decorators)
        }

        monthView.init(
            data.month, data.cells, data.displayOnly,
            data.titleTypeface, data.dateTypeface, data.deactivatedDates, arrayListOf())

    }
}