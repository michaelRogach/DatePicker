package com.example.datepicker

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.datepicker.adapter.MonthAdapter
import com.example.datepicker.adapter.data_holders.HeaderDH
import com.example.datepicker.adapter.data_holders.MonthDH
import java.text.SimpleDateFormat
import java.util.*


class CalendarPickerLightView(context: Context, attrs: AttributeSet?) : RecyclerView(context, attrs) {

    private var adapter: MonthAdapter? = null
    private val cells = IndexedLinkedHashMap<String, List<List<MonthCellDescriptor>>>()
    private val months: MutableList<MonthDescriptor> = ArrayList()
    private var deactivatedDates = ArrayList<Int>()
    private var locale = Locale.getDefault()
    private var timeZone = TimeZone.getDefault()
    lateinit var minCal: Calendar
    lateinit var maxCal: Calendar
    lateinit var monthCounter: Calendar
    private var today = Calendar.getInstance()
    private var dividerColor: Int = -1
    private var dayBackgroundResId: Int = -1
    private var dayTextColorResId: Int = -1
    private var titleTextColor: Int = -1
    private var displayHeader: Boolean = false
    private var headerTextColor: Int = -1
    private var titleTypeface: Typeface? = null
    private var dateTypeface: Typeface? = null
    private var monthsReverseOrder = false
    private var items = arrayListOf<Any>()
    private var minDate = Date()
    private var maxDate = Date()
    private var itemClickListener: MonthAdapter.IClickListener? = null

    init {
        obtainStyles(attrs)
        initLayoutManager()
    }

    private fun obtainStyles(attrs: AttributeSet?) {
        val res = context.resources
        val a = context.obtainStyledAttributes(attrs, R.styleable.CalendarPickerLightView)
        val bg = a.getColor(
            R.styleable.CalendarPickerView_android_background,
            res.getColor(R.color.calendar_bg))
        dividerColor = a.getColor(
            R.styleable.CalendarPickerLightView_dividerColor,
            res.getColor(R.color.white))
        dayBackgroundResId = a.getResourceId(
            R.styleable.CalendarPickerLightView_dayBackground,
            R.drawable.calendar_grid_bg_selector)
        dayTextColorResId = a.getResourceId(
            R.styleable.CalendarPickerLightView_dayTextColor,
            R.drawable.day_grid_text_color)
        titleTextColor = a.getColor(
            R.styleable.CalendarPickerLightView_titleTextColor,
            res.getColor(R.color.dateTimeRangePickerTitleTextColor))
        displayHeader = a.getBoolean(R.styleable.CalendarPickerLightView_displayHeader, true)
        headerTextColor = a.getColor(
            R.styleable.CalendarPickerLightView_headerTextColor,
            res.getColor(R.color.dateTimeRangePickerHeaderTextColor))
        a.recycle()
        setBackgroundColor(bg)
    }

    private fun initLayoutManager() {
        val gridDecorator = GridSpaceItemDecoration(20)
        addItemDecoration(gridDecorator)
        val layoutManager = GridLayoutManager(context, 3, VERTICAL, monthsReverseOrder)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == 0 || position % 13 == 0) 3 else 1
            }
        }
        setLayoutManager(layoutManager)
    }

    fun initialize(minDate: Date, maxDate: Date, listener: MonthAdapter.IClickListener) {
        adapter = MonthAdapter(prepareStyleData(), listener)
        clearPreviousStates()
        initCalendars(minDate, maxDate)
        prepareItems()
        scrollToSelectedDates()
        setAdapter(adapter)
    }

    private fun initCalendars(minDate: Date, maxDate: Date) {
        this.minDate = minDate
        this.maxDate = maxDate
        minCal = Calendar.getInstance(timeZone, locale)
        maxCal = Calendar.getInstance(timeZone, locale)
        monthCounter = Calendar.getInstance(timeZone, locale)
        minCal.time = minDate
        maxCal.time = maxDate
        minCal.apply {
            set(Calendar.MONTH, 0)
            set(Calendar.DAY_OF_MONTH, 1)
        }
        maxCal.apply {
            set(Calendar.MONTH, 0)
            set(Calendar.DAY_OF_MONTH, 1)
        }
        setMidnight(minCal)
        setMidnight(maxCal)

        // maxDate is exclusive: bump back to the previous day so if maxDate is the first of a month,
        // we don't accidentally include that month in the view.
        maxCal.add(Calendar.MILLISECOND, -1)

        // Now iterate between minCal and maxCal and build up our list of months to show.
        monthCounter.apply {
            time = minCal.time
            set(Calendar.MONTH, 0)
            set(Calendar.DAY_OF_MONTH, 1)
        }
    }

    private fun prepareItems() {
        val maxMonth = maxCal.get(Calendar.MONTH)
        val maxYear = maxCal.get(Calendar.YEAR)
        val monthNameFormat = SimpleDateFormat("MMM", Locale.getDefault())
        monthNameFormat.timeZone = timeZone

        while ((monthCounter.get(Calendar.MONTH) <= maxMonth // Up to, including the month.
                    || monthCounter.get(Calendar.YEAR) < maxYear) // Up to the year.
            && monthCounter.get(Calendar.YEAR) < maxYear + 1) { // But not > next yr.
            val date = monthCounter.time
            val month = MonthDescriptor(
                monthCounter.get(Calendar.MONTH), monthCounter.get(Calendar.YEAR), date,
                monthNameFormat.format(date) ?: "")
            cells[monthKey(month)] = getMonthCells(month, monthCounter)
            months.add(month)
            monthCounter.add(Calendar.MONTH, 1)
        }

        months.chunked(12).forEach {
            val monthDescriptor = it.first()
            items.add(HeaderDH(monthDescriptor.date.time, SimpleDateFormat("yyyy", Locale.getDefault()).format(monthDescriptor.date)))
            prepareDHs(it)
        }
        adapter?.submitList(items)
    }

    private fun prepareDHs(months: List<MonthDescriptor>) {
        months.forEachIndexed { index, month ->
            items.add(
                MonthDH(
                    month,
                    cells.getValueAtIndex(cells.getIndexOfKey(monthKey(month))) ?: emptyList(),
                    true, titleTypeface, dateTypeface, deactivatedDates
                ))
        }
    }

    private fun scrollToSelectedMonth(selectedIndex: Int, smoothScroll: Boolean = false) {
        post {
            if (smoothScroll) {
                smoothScrollToPosition(selectedIndex)
            } else {
                scrollToPosition(selectedIndex)
            }
        }
    }

    private fun scrollToSelectedDates() {
        val today = Calendar.getInstance(timeZone, locale)
        val selectedIndex = items.indexOfFirst { it is MonthDH && sameMonth(today, it.month) }
        if (selectedIndex != -1)
            scrollToSelectedMonth(selectedIndex)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        check(!months.isEmpty()) { "Must have at least one month to display.  Did you forget to call init()?" }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    private fun prepareStyleData(): MonthView.StyleData {
        return MonthView.StyleData(
            today, dividerColor,
            dayBackgroundResId, dayTextColorResId, false,
            headerTextColor, locale, GridDayViewAdapter(), null
        )
    }

    private fun monthKey(month: MonthDescriptor): String {
        return month.year.toString() + "-" + month.month
    }

    private fun getMonthCells(month: MonthDescriptor, startCal: Calendar?): List<List<MonthCellDescriptor>> {
        val cal = Calendar.getInstance(timeZone, locale)
        cal.time = startCal!!.time
        val cells: MutableList<List<MonthCellDescriptor>> = ArrayList()
        cal[Calendar.DAY_OF_MONTH] = 1
        val firstDayOfWeek = cal[Calendar.DAY_OF_WEEK]
        var offset = cal.firstDayOfWeek - firstDayOfWeek
        if (offset > 0) {
            offset -= 7
        }
        cal.add(Calendar.DATE, offset)
        while ((cal[Calendar.MONTH] < month.month + 1 || cal[Calendar.YEAR] < month.year) //
            && cal[Calendar.YEAR] <= month.year) {
            val weekCells: MutableList<MonthCellDescriptor> = ArrayList()
            cells.add(weekCells)
            for (c in 0..6) {
                val date = cal.time
                val isCurrentMonth = cal[Calendar.MONTH] == month.month
                val isSelectable = false
                val isToday = sameDate(cal, today)
                val isSelected = isToday
                val isHighlighted = false
                val value = cal[Calendar.DAY_OF_MONTH]
                var rangeState = RangeState.NONE
                weekCells.add(
                    MonthCellDescriptor(
                        date, isCurrentMonth, isSelectable, isSelected, isToday,
                        isHighlighted, value, rangeState))
                cal.add(Calendar.DATE, 1)
            }
        }
        return cells
    }

    private fun clearPreviousStates() {
        // Clear previous state.
        cells.clear()
        months.clear()
        items.clear()
    }

    /**
     * Interface to be notified when a new date is selected or unselected. This will only be called
     * when the user initiates the date selection.  If you call [.selectDate] this
     * listener will not be notified.
     *
     * @see .setOnDateSelectedListener
     */
    interface OnDateSelectedListener {
        fun onDateSelected(date: Date?)
        fun onDateUnselected(date: Date?)
    }

    companion object {

        /**
         * Clears out the hours/minutes/seconds/millis of a Calendar.
         */
        fun setMidnight(cal: Calendar) {
            cal[Calendar.HOUR_OF_DAY] = 0
            cal[Calendar.MINUTE] = 0
            cal[Calendar.SECOND] = 0
            cal[Calendar.MILLISECOND] = 0
        }

        private fun sameDate(cal: Calendar?, selectedDate: Calendar?): Boolean {
            return cal!![Calendar.MONTH] == selectedDate!![Calendar.MONTH] && cal[Calendar.YEAR] == selectedDate[Calendar.YEAR] && cal[Calendar.DAY_OF_MONTH] == selectedDate[Calendar.DAY_OF_MONTH]
        }

        private fun sameMonth(cal: Calendar, month: MonthDescriptor): Boolean {
            return cal[Calendar.MONTH] == month.month && cal[Calendar.YEAR] == month.year
        }
    }
}