package com.doubleclick.doctorapp.android.views.slycalendarview.listeners

import java.util.*

interface DateSelectListener {
    fun dateSelect(selectedDate: Date)
    fun dateLongSelect(selectedDate: Date)
}