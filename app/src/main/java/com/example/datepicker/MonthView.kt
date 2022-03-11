package com.example.datepicker

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import java.text.DateFormat
import java.text.NumberFormat
import java.util.*

class MonthView(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs) {
    var title: TextView? = null
    var grid: CalendarGridView? = null
    var listener: Listener? = null
    private var decorators: List<CalendarCellDecorator>? = null
    var isRtl = false
    var locale: Locale? = null
    var deactivatedDates: ArrayList<Int>? = null
    fun setDecorators(decorators: List<CalendarCellDecorator>?) {
        this.decorators = decorators
    }

    fun getDecorators(): List<CalendarCellDecorator>? {
        return decorators
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        title = findViewById<View>(R.id.title) as TextView
        grid = findViewById<View>(R.id.calendar_grid) as CalendarGridView
    }

    fun init(
        month: MonthDescriptor, cells: List<List<MonthCellDescriptor>>,
        displayOnly: Boolean, titleTypeface: Typeface?, dateTypeface: Typeface?,
        deactivatedDates: ArrayList<Int>, subTitles: ArrayList<SubTitle>?
    ) {

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
                        val subTitle = SubTitle.getByDate(subTitles, cell.date)
                        if (subTitle != null && (subTitleTextView?.text?.equals(subTitle.title) == true).not()) {
                            subTitleTextView?.text = subTitle.title
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

                    if (null != decorators) {
                        for (decorator in decorators!!) {
                            decorator.decorate(cellView, cell.date)
                        }
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
        fun create(
            parent: ViewGroup?, inflater: LayoutInflater,
            weekdayNameFormat: DateFormat, listener: Listener?, today: Calendar, dividerColor: Int,
            dayBackgroundResId: Int, dayTextColorResId: Int, titleTextColor: Int, displayHeader: Boolean,
            headerTextColor: Int, locale: Locale, adapter: DayViewAdapter
        ): MonthView {
            return create(
                parent, inflater, weekdayNameFormat, listener, today, dividerColor,
                dayBackgroundResId, dayTextColorResId, titleTextColor, displayHeader, headerTextColor, null,
                locale, adapter)
        }

        fun create(
            parent: ViewGroup?, inflater: LayoutInflater,
            weekdayNameFormat: DateFormat, listener: Listener?, today: Calendar, dividerColor: Int,
            dayBackgroundResId: Int, dayTextColorResId: Int, titleTextColor: Int, displayHeader: Boolean,
            headerTextColor: Int, decorators: List<CalendarCellDecorator>?, locale: Locale,
            adapter: DayViewAdapter
        ): MonthView {
            val view = inflater.inflate(R.layout.month, parent, false) as MonthView

            view.apply {
                setDayViewAdapter(adapter)
                setDividerColor(dividerColor)
                setDayTextColor(dayTextColorResId)
                setTitleTextColor(titleTextColor)
                setDisplayHeader(displayHeader)
                setHeaderTextColor(headerTextColor)
                if (dayBackgroundResId != 0) {
                    setDayBackground(dayBackgroundResId)
                }
                isRtl = isRtl(locale)
                this.locale = locale
            }
            val originalDayOfWeek = today[Calendar.DAY_OF_WEEK]
//            val firstDayOfWeek = today.firstDayOfWeek
//            val headerRow: CalendarRowView = view.grid?.getChildAt(0) as CalendarRowView
//            for (offset in 0..6) {
//                today[Calendar.DAY_OF_WEEK] = getDayOfWeek(firstDayOfWeek, offset, view.isRtl)
//                val textView = headerRow.getChildAt(offset) as TextView
//                textView.text = weekdayNameFormat.format(today.time)
//            }
            today[Calendar.DAY_OF_WEEK] = originalDayOfWeek
            view.listener = listener
            view.decorators = decorators
            return view
        }

        private fun getDayOfWeek(firstDayOfWeek: Int, offset: Int, isRtl: Boolean): Int {
            val dayOfWeek = firstDayOfWeek + offset
            return if (isRtl) {
                8 - dayOfWeek
            } else dayOfWeek
        }

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
//            listener = data.listener
        setDecorators(styleData.decorators)
    }

    data class StyleData(
        val today: Calendar,
        val dividerColor: Int,
        val dayBackgroundResId: Int,
        val dayTextColorResId: Int,
        val titleTextColor: Int,
        val displayHeader: Boolean,
        val headerTextColor: Int,
        val decorators: List<CalendarCellDecorator>?,
        val locale: Locale,
        val adapter: DayViewAdapter
    )
}