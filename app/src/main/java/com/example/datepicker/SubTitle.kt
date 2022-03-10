package com.example.datepicker

import com.example.datepicker.SubTitle
import java.util.*

class SubTitle(val date: Date, val title: String) {

    companion object {
        fun getByDate(subTitles: ArrayList<SubTitle>?, date: Date): SubTitle? {
            if (subTitles != null && subTitles.size > 0) {
                for (subTitle in subTitles) {
                    if (isSameDay(subTitle.date, date)) {
                        return subTitle
                    }
                }
            }
            return null
        }

        private fun isSameDay(date1: Date, date2: Date): Boolean {
            val cal1 = Calendar.getInstance()
            val cal2 = Calendar.getInstance()
            cal1.time = date1
            cal2.time = date2
            return cal1[Calendar.DAY_OF_YEAR] == cal2[Calendar.DAY_OF_YEAR] && cal1[Calendar.YEAR] == cal2[Calendar.YEAR]
        }
    }
}