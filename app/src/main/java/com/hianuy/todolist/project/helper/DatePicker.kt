package com.hianuy.todolist.project.helper

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TimePicker
import com.hianuy.todolist.R
import com.hianuy.todolist.project.extension.toText
import java.util.*

class DatePicker(val context: Context, val edtDeadline: EditText) :
    DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {
    var day = 0
    var month = 0
    var year = 0
    var hour = 0
    var minute = 0

    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0
    var savedHour = 0
    var savedMinute = 0

    private fun getTimeCalendar() {
        val calendar = Calendar.getInstance()
        day = calendar.get(Calendar.DAY_OF_MONTH)
        month = calendar.get(Calendar.MONTH)
        year = calendar.get(Calendar.YEAR)
        hour = calendar.get(Calendar.HOUR)
        minute = calendar.get(Calendar.MINUTE)
    }

    fun pickDate() {
        edtDeadline.setOnClickListener {
            getTimeCalendar()
            DatePickerDialog(context, this, year, month, day).show()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month
        savedYear = year
        getTimeCalendar()
        TimePickerDialog(context, this, hour, minute, true).show()
    }

    @SuppressLint("SetTextI18n")
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {

        savedHour = hourOfDay
        savedMinute = minute
        if (savedHour in 0..9) {
            edtDeadline.text = "$savedDay/$savedMonth/$savedYear 0$savedHour:$savedMinute".toText()
            Log.i("tess", "onTimeSet: $savedHour/$savedMinute")
        }
        else if (savedHour in 10..24) {
            edtDeadline.text = "$savedDay/$savedMonth/$savedYear $savedHour:$savedMinute".toText()
        }
        if (savedMinute in 0..9) {
            edtDeadline.text = "$savedDay/$savedMonth/$savedYear $savedHour:0$savedMinute".toText()
        }
        else if (savedMinute in 10..24) {
            edtDeadline.text = "$savedDay/$savedMonth/$savedYear $savedHour:$savedMinute".toText()
        }
    }
}