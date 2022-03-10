package com.example.datepicker

import android.view.ContextThemeWrapper
import android.widget.TextView
import com.example.datepicker.R
import android.view.Gravity

class DefaultDayViewAdapter : DayViewAdapter {

    override fun makeCellView(parent: CalendarCellView) {
        val context = parent.context

        val subTitleTextView = TextView(ContextThemeWrapper(context, R.style.CalendarCell_SubTitle))
        subTitleTextView.isDuplicateParentStateEnabled = true
        subTitleTextView.gravity = Gravity.BOTTOM
        parent.addView(subTitleTextView)

        val textView = TextView(ContextThemeWrapper(context, R.style.CalendarCell_CalendarDate))
        textView.isDuplicateParentStateEnabled = true
        parent.addView(textView)

        parent.dayOfMonthTextView = textView
        parent.subTitleTextView = subTitleTextView
    }
}