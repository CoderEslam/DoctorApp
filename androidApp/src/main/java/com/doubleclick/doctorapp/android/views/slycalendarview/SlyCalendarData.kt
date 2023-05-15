package com.doubleclick.doctorapp.android.views.slycalendarview

import java.util.*

class SlyCalendarData {
    private var showDate: Date? = null //current showing date

    private var selectedStartDate: Date? = null // first selected date

    private var selectedEndDate: Date? = null // ended selected date

    private var selectedHour = 0
    private var selectedMinutes = 0


    private var firstMonday = true // is first monday

    private var single = true

    private var backgroundColor: Int? = null
    private var headerColor: Int? = null
    private var headerTextColor: Int? = null
    private var textColor: Int? = null
    private var selectedColor: Int? = null
    private var selectedTextColor: Int? = null
    private var timeTheme: Int? = null

    fun getShowDate(): Date? {
        if (showDate == null) {
            showDate = if (selectedStartDate != null) {
                selectedStartDate!!.clone() as Date
            } else {
                Calendar.getInstance().time
            }
        }
        return showDate
    }

    fun setShowDate(showDate: Date?) {
        this.showDate = showDate
    }

    fun getSelectedStartDate(): Date? {
        return selectedStartDate
    }

    fun setSelectedStartDate(selectedStartDate: Date?) {
        this.selectedStartDate = selectedStartDate
    }

    fun getSelectedEndDate(): Date? {
        return selectedEndDate
    }

    fun setSelectedEndDate(selectedEndDate: Date?) {
        this.selectedEndDate = selectedEndDate
    }

    fun getSelectedHour(): Int {
        return selectedHour
    }

    fun setSelectedHour(selectedHour: Int) {
        this.selectedHour = selectedHour
    }

    fun getSelectedMinutes(): Int {
        return selectedMinutes
    }

    fun setSelectedMinutes(selectedMinutes: Int) {
        this.selectedMinutes = selectedMinutes
    }

    fun isFirstMonday(): Boolean {
        return firstMonday
    }

    fun setFirstMonday(firstMonday: Boolean) {
        this.firstMonday = firstMonday
    }

    fun getBackgroundColor(): Int? {
        return backgroundColor
    }

    fun setBackgroundColor(backgroundColor: Int?) {
        this.backgroundColor = backgroundColor
    }

    fun getHeaderColor(): Int? {
        return headerColor
    }

    fun setHeaderColor(headerColor: Int?) {
        this.headerColor = headerColor
    }

    fun getHeaderTextColor(): Int? {
        return headerTextColor
    }

    fun setHeaderTextColor(headerTextColor: Int?) {
        this.headerTextColor = headerTextColor
    }

    fun getTextColor(): Int? {
        return textColor
    }

    fun setTextColor(textColor: Int?) {
        this.textColor = textColor
    }

    fun getSelectedColor(): Int? {
        return selectedColor
    }

    fun setSelectedColor(selectedColor: Int?) {
        this.selectedColor = selectedColor
    }

    fun getSelectedTextColor(): Int? {
        return selectedTextColor
    }

    fun setSelectedTextColor(selectedTextColor: Int?) {
        this.selectedTextColor = selectedTextColor
    }

    fun isSingle(): Boolean {
        return single
    }

    fun setSingle(single: Boolean) {
        this.single = single
    }

    fun getTimeTheme(): Int? {
        return timeTheme
    }

    fun setTimeTheme(timeTheme: Int?) {
        this.timeTheme = timeTheme
    }
}
