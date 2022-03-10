package com.example.datepicker

import java.util.*

class MonthDescriptor(val month: Int, val year: Int, val date: Date, var label: String) {
    override fun toString(): String {
        return ("MonthDescriptor{"
                + "label='"
                + label
                + '\''
                + ", month="
                + month
                + ", year="
                + year
                + '}')
    }
}