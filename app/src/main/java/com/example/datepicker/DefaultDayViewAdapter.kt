package com.example.datepicker

import android.view.ContextThemeWrapper
import android.widget.TextView

class DefaultDayViewAdapter : DayViewAdapter {

    override fun makeCellView(parent: CalendarCellView) {
        val context = parent.context

        val textView = TextView(ContextThemeWrapper(context, R.style.CalendarCell_CalendarDate))
        textView.isDuplicateParentStateEnabled = true
        parent.addView(textView)

        parent.dayOfMonthTextView = textView
    }
}