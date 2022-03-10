package com.example.datepicker

/** Adapter used to provide a layout for [CalendarCellView]. */
interface DayViewAdapter {
    fun makeCellView(parent: CalendarCellView)
}