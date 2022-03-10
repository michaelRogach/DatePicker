package com.example.datepicker.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.example.datepicker.CalendarPickerView
import com.example.datepicker.R
import com.example.datepicker.SubTitle
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
        var button = findViewById<AppCompatButton>(R.id.get_selected_dates)
        val list = ArrayList<Int?>()
        //        list.add(2);
        calendar.deactivateDates(list)
        val arrayList = ArrayList<Date?>()
        try {
            val dateformat = SimpleDateFormat("dd-MM-yyyy")
            val strdate = "09-3-2022"
            val strdate2 = "08-3-2022"
            val strdate3 = "07-3-2022"
            val strdate4 = "06-3-2022"
            val strdate5 = "05-3-2022"
            val strdate6 = "04-3-2022"
            val newdate = dateformat.parse(strdate)
            val newdate2 = dateformat.parse(strdate2)
            val newdate3 = dateformat.parse(strdate3)
            val newdate4 = dateformat.parse(strdate4)
            val newdate5 = dateformat.parse(strdate5)
            val newdate6 = dateformat.parse(strdate6)
            arrayList.add(newdate)
            arrayList.add(newdate2)
            arrayList.add(newdate3)
            arrayList.add(newdate4)
            arrayList.add(newdate5)
            arrayList.add(newdate6)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        calendar.init(lastYear.time, nextYear.time, SimpleDateFormat("MMMM, yyyy", Locale.getDefault())) //
            .inMode(CalendarPickerView.SelectionMode.RANGE)
            .withHighlightedDates(arrayList)
        calendar.scrollToDate(Date())
        button.setOnClickListener(View.OnClickListener {
            Toast.makeText(this@SampleActivity, "list " + calendar.selectedDates.toString(), Toast.LENGTH_LONG).show()
        })
    }
}