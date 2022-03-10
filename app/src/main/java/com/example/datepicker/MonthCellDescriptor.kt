package com.example.datepicker

import java.util.*

/**
 * Describes the state of a particular date cell in a [MonthView].
 */
data class MonthCellDescriptor(
    val date: Date,
    val isCurrentMonth: Boolean,
    val isSelectable: Boolean,
    var isSelected: Boolean,
    val isToday: Boolean,
    var isHighlighted: Boolean,
    val value: Int,
    var rangeState: RangeState
) {
    var isDeactivated = false
    var isMarked = false

}