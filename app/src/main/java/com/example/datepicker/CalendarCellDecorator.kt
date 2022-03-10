package com.example.datepicker

import java.util.*

interface CalendarCellDecorator {
    fun decorate(cellView: CalendarCellView?, date: Date?)
}