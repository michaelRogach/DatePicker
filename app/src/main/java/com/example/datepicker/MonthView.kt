package com.example.datepicker

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import java.text.NumberFormat
import java.util.*

class MonthView(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs) {
    var title: TextView? = null
    var grid: CalendarGridView? = null
    var listener: Listener? = null
    var isRtl = false
    var locale: Locale? = null
    var deactivatedDates: ArrayList<Int>? = null

    override fun onFinishInflate() {
        super.onFinishInflate()
        title = findViewById<View>(R.id.title) as TextView
        grid = findViewById<View>(R.id.calendar_grid) as CalendarGridView
    }

    fun init(
        month: MonthDescriptor, cells: List<List<MonthCellDescriptor>>, displayOnly: Boolean,
        titleTypeface: Typeface?, dateTypeface: Typeface?, deactivatedDates: ArrayList<Int>) {

        title?.text = month.label
        val numberFormatter = NumberFormat.getInstance(locale)
        val numRows = cells.size
        grid?.setNumRows(numRows)
        for (i in 0..4) {
            val weekRow = grid?.getChildAt(i + 1) as CalendarRowView
            weekRow.setListener(listener)
            if (i < numRows) {
                weekRow.visibility = VISIBLE
                val week: List<MonthCellDescriptor> = cells[i]
                for (c in week.indices) {

                    val cell: MonthCellDescriptor = week[if (isRtl) 6 - c else c]
                    val cellDate: String = numberFormatter.format(cell.value)

                    val cellView = weekRow.getChildAt(c) as CalendarCellView
                    cellView.apply {

                        if ((dayOfMonthTextView?.text?.equals(cellDate) == true).not()) {
                            dayOfMonthTextView?.text = cellDate
                        }

                        isEnabled = cell.isCurrentMonth
                        val dayOfWeek = c + 1
                        isClickable = if (deactivatedDates.contains(dayOfWeek)) false else !displayOnly
                        if (deactivatedDates.contains(dayOfWeek)) {
                            setSelectable(cell.isSelectable)
                            isSelected = false
                            setCurrentMonth(cell.isCurrentMonth)
                            setToday(cell.isToday)
                            setRangeState(cell.rangeState)
                            setHighlighted(cell.isHighlighted)
                            setRangeMarked(cell.isMarked)
                            setDeactivated(true)
                        } else {
                            setSelectable(cell.isSelectable)
                            isSelected = cell.isSelected
                            setCurrentMonth(cell.isCurrentMonth)
                            setToday(cell.isToday)
                            setRangeState(cell.rangeState)
                            setHighlighted(cell.isHighlighted)
                            setRangeMarked(cell.isMarked)
                            setDeactivated(false)
                        }
                        tag = cell

                    }

                }
            } else {
                weekRow.visibility = GONE
            }
        }
        if (titleTypeface != null) {
            title!!.typeface = titleTypeface
        }
        if (dateTypeface != null) {
            grid?.setTypeface(dateTypeface)
        }
    }

    fun setDividerColor(color: Int) {
        grid?.setDividerColor(color)
    }

    fun setDayBackground(resId: Int) {
        grid?.setDayBackground(resId)
    }

    fun setDayTextColor(resId: Int) {
        grid?.setDayTextColor(resId)
    }

    fun setDayViewAdapter(adapter: DayViewAdapter) {
        grid?.setDayViewAdapter(adapter)
    }

    fun setTitleTextColor(color: Int) {
        title!!.setTextColor(color)
    }

    fun setDisplayHeader(displayHeader: Boolean) {
        grid?.setDisplayHeader(displayHeader)
    }

    fun setHeaderTextColor(color: Int) {
        grid?.setHeaderTextColor(color)
    }

    interface Listener {
        fun handleClick(cell: MonthCellDescriptor?)
    }

    companion object {

        private fun isRtl(locale: Locale): Boolean {
            // TODO convert the build to gradle and use getLayoutDirection instead of this (on 17+)?
            val directionality = Character.getDirectionality(locale.getDisplayName(locale)[0]).toInt()
            return directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT.toInt() || directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC.toInt()
        }
    }

    fun setUpView(styleData: StyleData) {
        setDividerColor(styleData.dividerColor)
        setDayTextColor(styleData.dayTextColorResId)
        setTitleTextColor(styleData.titleTextColor)
        setDisplayHeader(styleData.displayHeader)
        setHeaderTextColor(styleData.headerTextColor)
        if (styleData.dayBackgroundResId != 0) {
            setDayBackground(styleData.dayBackgroundResId)
        }
        isRtl = isRtl(styleData.locale)
        this.locale = styleData.locale

        val originalDayOfWeek = styleData.today[Calendar.DAY_OF_WEEK]
        styleData.today[Calendar.DAY_OF_WEEK] = originalDayOfWeek
        listener = styleData.listener
    }

    data class StyleData(
        val today: Calendar,
        val dividerColor: Int,
        val dayBackgroundResId: Int,
        val dayTextColorResId: Int,
        val titleTextColor: Int,
        val displayHeader: Boolean,
        val headerTextColor: Int,
        val locale: Locale,
        val adapter: DayViewAdapter,
        val listener: Listener
    )
}