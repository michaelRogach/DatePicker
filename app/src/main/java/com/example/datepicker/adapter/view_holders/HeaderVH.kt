package com.example.datepicker.adapter.view_holders

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.example.datepicker.R
import com.example.datepicker.adapter.DiffVH
import com.example.datepicker.adapter.data_holders.HeaderDH

class HeaderVH(containerView: View) : DiffVH<HeaderDH>(containerView) {

    private val textDate = itemView.findViewById<AppCompatTextView>(R.id.textDate)

    override fun render(data: HeaderDH) {
        renderTitle()
    }

    override fun render(data: HeaderDH, payloads: Set<String>) {
        renderTitle()
    }

    private fun renderTitle() {
        textDate.text = data.title
    }
}