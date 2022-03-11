package com.example.datepicker.adapter.data_holders

import android.graphics.Typeface
import com.example.datepicker.CalendarCellDecorator
import com.example.datepicker.DayViewAdapter
import com.example.datepicker.MonthCellDescriptor
import com.example.datepicker.MonthDescriptor
import java.util.*

class MonthDH(
    val month: MonthDescriptor,
    val cells: List<List<MonthCellDescriptor>>,
    val today: Calendar,
    val dividerColor: Int,
    val dayBackgroundResId: Int,
    val dayTextColorResId: Int,
    val titleTextColor: Int,
    val displayHeader: Boolean,
    val headerTextColor: Int,
    val decorators: List<CalendarCellDecorator>?,
    val locale: Locale,
    val adapter: DayViewAdapter,

    val displayOnly: Boolean,
    val titleTypeface: Typeface?,
    val dateTypeface: Typeface?,
    val deactivatedDates: ArrayList<Int>,
)