package com.example.datepicker

import android.widget.FrameLayout
import android.widget.TextView
import android.content.Context
import android.util.AttributeSet

class CalendarCellView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {
    private var isSelectable = false
    private var isCurrentMonth = false
    private var isToday = false
    private var isHighlighted = false
    private var isMarked = false
    private var isDeactivated = false
    private var rangeState = RangeState.NONE

    //textView.setTextSize(8);
    var dayOfMonthTextView: TextView? = null
        get() {
            checkNotNull(field) { "You have to setDayOfMonthTextView in your custom DayViewAdapter." }
            return field
        }
    var subTitleTextView: TextView? = null
        get() {
            checkNotNull(field) { "You have to setSubTitleTextView in your custom DayViewAdapter." }
            return field
        }

    fun setSelectable(isSelectable: Boolean) {
        if (this.isSelectable != isSelectable) {
            this.isSelectable = isSelectable
            refreshDrawableState()
        }
    }

    fun setCurrentMonth(isCurrentMonth: Boolean) {
        if (this.isCurrentMonth != isCurrentMonth) {
            this.isCurrentMonth = isCurrentMonth
            refreshDrawableState()
        }
    }

    fun setToday(isToday: Boolean) {
        if (this.isToday != isToday) {
            this.isToday = isToday
            refreshDrawableState()
        }
    }

    fun setRangeState(rangeState: RangeState) {
        if (this.rangeState !== rangeState) {
            this.rangeState = rangeState
            refreshDrawableState()
        }
    }

    fun setHighlighted(isHighlighted: Boolean) {
        if (this.isHighlighted != isHighlighted) {
            this.isHighlighted = isHighlighted
            refreshDrawableState()
        }
    }

    fun setRangeMarked(isMarked: Boolean) {
        if (this.isMarked != isMarked) {
            this.isMarked = isMarked
            refreshDrawableState()
        }
    }

    fun setDeactivated(isDeactivated: Boolean) {
        if (this.isDeactivated != isDeactivated) {
            this.isDeactivated = isDeactivated
            refreshDrawableState()
        }
    }

    fun isCurrentMonth(): Boolean {
        return isCurrentMonth
    }

    fun isToday(): Boolean {
        return isToday
    }

    fun isSelectable(): Boolean {
        return isSelectable
    }

    fun isHighlighted(): Boolean {
        return isHighlighted
    }

    fun getRangeState(): RangeState {
        return rangeState
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + 5)
        if (isSelectable) {
            mergeDrawableStates(drawableState, STATE_SELECTABLE)
        }
        if (isCurrentMonth) {
            mergeDrawableStates(drawableState, STATE_CURRENT_MONTH)
        }
        if (isToday) {
            mergeDrawableStates(drawableState, STATE_TODAY)
        }
        if (isHighlighted) {
            mergeDrawableStates(drawableState, STATE_HIGHLIGHTED)
        }
        if (isMarked) {
            mergeDrawableStates(drawableState, STATE_MARKED)
        }
        if (isDeactivated) {
            mergeDrawableStates(drawableState, STATE_DEACTIVATED)
        }
        if (rangeState === RangeState.FIRST) {
            mergeDrawableStates(drawableState, STATE_RANGE_FIRST)
        } else if (rangeState === RangeState.MIDDLE) {
            mergeDrawableStates(drawableState, STATE_RANGE_MIDDLE)
        } else if (rangeState === RangeState.LAST) {
            mergeDrawableStates(drawableState, STATE_RANGE_LAST)
        }
        return drawableState
    }

    companion object {
        private val STATE_SELECTABLE = intArrayOf(
            R.attr.tsquare_state_selectable
        )
        private val STATE_CURRENT_MONTH = intArrayOf(
            R.attr.tsquare_state_current_month
        )
        private val STATE_TODAY = intArrayOf(
            R.attr.tsquare_state_today
        )
        private val STATE_HIGHLIGHTED = intArrayOf(
            R.attr.tsquare_state_highlighted
        )
        private val STATE_RANGE_FIRST = intArrayOf(
            R.attr.tsquare_state_range_first
        )
        private val STATE_RANGE_MIDDLE = intArrayOf(
            R.attr.tsquare_state_range_middle
        )
        private val STATE_RANGE_LAST = intArrayOf(
            R.attr.tsquare_state_range_last
        )
        private val STATE_MARKED = intArrayOf(
            R.attr.tsquare_state_marked
        )
        private val STATE_DEACTIVATED = intArrayOf(
            R.attr.tsquare_state_deactivated
        )
    }
}