package com.example.datepicker.adapter.data_holders

import android.graphics.Typeface
import com.example.datepicker.MonthCellDescriptor
import com.example.datepicker.MonthDescriptor

class MonthDH(
    val month: MonthDescriptor,
    val cells: List<List<MonthCellDescriptor>>,
    val displayOnly: Boolean,
    val titleTypeface: Typeface?,
    val dateTypeface: Typeface?,
    val deactivatedDates: ArrayList<Int>,
)