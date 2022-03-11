package com.example.datepicker.activity

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.datepicker.CalendarPickerView
import com.example.datepicker.CalendarRowView
import com.example.datepicker.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class SampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)
        val nextYear = Calendar.getInstance()
        nextYear.add(Calendar.YEAR, 10)
        val lastYear = Calendar.getInstance()
        lastYear.add(Calendar.YEAR, -10)
        var calendar = findViewById<CalendarPickerView>(R.id.calendar_view)
        var daysNames = findViewById<CalendarRowView>(R.id.daysNames)
//        val list = ArrayList<Int>()
        //        list.add(2);
//        calendar.deactivateDates(list)
        val arrayList = ArrayList<Date>()
        val selected = ArrayList<Date>()
        try {
            val dateformat = SimpleDateFormat("dd-MM-yyyy")
            val strdate = "09-3-2022"
            val strdate2 = "08-3-2022"
            val strdate3 = "07-3-2022"
            val strdate4 = "06-3-2022"
            val strdate5 = "05-3-2022"
            val strdate6 = "04-3-2022"
            val strdate7 = "04-3-2022"
            val strdate8 = "09-3-2022"
            val newdate = dateformat.parse(strdate)
            val newdate2 = dateformat.parse(strdate2)
            val newdate3 = dateformat.parse(strdate3)
            val newdate4 = dateformat.parse(strdate4)
            val newdate5 = dateformat.parse(strdate5)
            val newdate6 = dateformat.parse(strdate6)
            val newdate7 = dateformat.parse(strdate7)
            val newdate8 = dateformat.parse(strdate8)
            arrayList.add(newdate)
            arrayList.add(newdate2)
            arrayList.add(newdate3)
            arrayList.add(newdate4)
            arrayList.add(newdate5)
            arrayList.add(newdate6)
            selected.add(newdate7)
            selected.add(newdate8)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        calendar.init(lastYear.time, nextYear.time, SimpleDateFormat("MMMM, yyyy", Locale.getDefault())) //
            .inMode(CalendarPickerView.SelectionMode.RANGE)
            .withHighlightedDates(arrayList)
            .withSelectedDates(selected)
//        calendar.scrollToDate(Date())
//        button.setOnClickListener(View.OnClickListener {
//            Toast.makeText(this@SampleActivity, "list " + calendar.selectedDates.toString(), Toast.LENGTH_LONG).show()
//        })
        setUpWeekNames(daysNames)
    }

    private fun setUpWeekNames(headerRow: CalendarRowView) {
        val timeZone = TimeZone.getDefault()
        val locale = Locale.getDefault()
        val weekdayNameFormat = SimpleDateFormat("EEE", locale).apply { this.timeZone = timeZone }
        val today = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault())
        val firstDayOfWeek = today.firstDayOfWeek
        for (offset in 0..6) {
            today[Calendar.DAY_OF_WEEK] = getDayOfWeek(firstDayOfWeek, offset, false)
            val textView = headerRow.getChildAt(offset) as TextView
            textView.text = weekdayNameFormat.format(today.time).first().toString()
        }
    }

    private fun getDayOfWeek(firstDayOfWeek: Int, offset: Int, isRtl: Boolean): Int {
        val dayOfWeek = firstDayOfWeek + offset
        return if (isRtl) {
            8 - dayOfWeek
        } else dayOfWeek
    }
}