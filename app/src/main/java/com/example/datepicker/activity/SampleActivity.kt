package com.example.datepicker.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.datepicker.R

class SampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)

        DatePickerFragment().show(
            supportFragmentManager, DatePickerFragment::class.java.canonicalName)
    }

}