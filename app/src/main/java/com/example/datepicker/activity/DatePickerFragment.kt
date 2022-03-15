package com.example.datepicker.activity

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.datepicker.CalendarPickerLightView
import com.example.datepicker.CalendarPickerView
import com.example.datepicker.CalendarRowView
import com.example.datepicker.R
import com.google.android.material.button.MaterialButton
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class DatePickerFragment() : BaseDialogFragment() {

    private var pickerType = CalendarPickerView.PickerType.MONTHLY

    override fun getTheme(): Int = R.style.YelloDialogOverlayTheme

    override fun getLayoutResId(): Int = R.layout.fragment_date_picker

    override fun initUI(savedInstanceState: Bundle?) {
        val flHeader = view?.findViewById<FrameLayout>(R.id.flHeader)
        val btnOk = view?.findViewById<MaterialButton>(R.id.btnOk)
        val btnChangeView = view?.findViewById<MaterialButton>(R.id.btnChangeView)
        var calendar = view?.findViewById<CalendarPickerView>(R.id.calendar_view)
        var calendarLight = view?.findViewById<CalendarPickerLightView>(R.id.calendar_light_view)
        var daysNames = view?.findViewById<CalendarRowView>(R.id.daysNames)
        btnOk?.setOnClickListener {
            dismiss()
        }

        btnChangeView?.setOnClickListener {
            pickerType = if (pickerType == CalendarPickerView.PickerType.MONTHLY) CalendarPickerView.PickerType.YEARLY else CalendarPickerView.PickerType.MONTHLY
            btnChangeView.text = if (pickerType == CalendarPickerView.PickerType.YEARLY) "Monthly" else "Yearly"
            calendar?.isVisible = pickerType == CalendarPickerView.PickerType.MONTHLY
            calendarLight?.isVisible = pickerType == CalendarPickerView.PickerType.YEARLY
            flHeader?.isVisible = pickerType == CalendarPickerView.PickerType.MONTHLY
        }


        val nextYear = Calendar.getInstance()
        nextYear.add(Calendar.YEAR, 1)
        val lastYear = Calendar.getInstance()
        lastYear.add(Calendar.YEAR, -10)

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
        calendarLight?.initialize(lastYear.time, nextYear.time)

        calendar?.initialize(lastYear.time, nextYear.time)
            ?.inMode(CalendarPickerView.SelectionMode.RANGE)
            ?.withHighlightedDates(arrayList)
            ?.withSelectedDates(selected)

        daysNames?.let { setUpWeekNames(it) }
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