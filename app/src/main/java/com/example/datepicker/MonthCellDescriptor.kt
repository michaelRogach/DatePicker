package com.example.datepicker

import com.example.datepicker.RangeState
import java.util.*

/**
 * Describes the state of a particular date cell in a [MonthView].
 */
class MonthCellDescriptor(
    val date: Date, val isCurrentMonth: Boolean, val isSelectable: Boolean, var isSelected: Boolean,
    val isToday: Boolean, var isHighlighted: Boolean, val value: Int, var rangeState: RangeState
) {
    var isDeactivated = false
    var isMarked = false
    override fun toString(): String {
        return ("MonthCellDescriptor{"
                + "date="
                + date
                + ", value="
                + value
                + ", isCurrentMonth="
                + isCurrentMonth
                + ", isSelected="
                + isSelected
                + ", isToday="
                + isToday
                + ", isSelectable="
                + isSelectable
                + ", isHighlighted="
                + isHighlighted
                + ", rangeState="
                + rangeState
                + "isDeactivated="
                + isDeactivated
                + '}')
    }
}