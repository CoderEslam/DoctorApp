package com.doubleclick.doctorapp.android.views.slycalendarview

import android.app.TimePickerDialog
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.doubleclick.doctorapp.android.R
import com.doubleclick.doctorapp.android.views.slycalendarview.listeners.DateSelectListener
import com.doubleclick.doctorapp.android.views.slycalendarview.listeners.DialogCompleteListener
import java.text.SimpleDateFormat
import java.util.*

class SlyCalendarView : FrameLayout, DateSelectListener {
    private var slyCalendarData: SlyCalendarData? = null
    private var callback: SlyCalendarDialog.Callback? = null
    private var completeListener: DialogCompleteListener? = null
    private var attrs: AttributeSet? = null
    private var defStyleAttr = 0

    constructor(context: Context?) : super(context!!) {
        init(null, 0)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!, attrs
    ) {
        this.attrs = attrs
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!, attrs, defStyleAttr
    ) {
        this.attrs = attrs
        this.defStyleAttr = defStyleAttr
    }

    fun setCallback(callback: SlyCalendarDialog.Callback?) {
        this.callback = callback
    }

    fun setCompleteListener(completeListener: DialogCompleteListener?) {
        this.completeListener = completeListener
    }

    fun setSlyCalendarData(slyCalendarData: SlyCalendarData) {
        this.slyCalendarData = slyCalendarData
        init(attrs, defStyleAttr)
        showCalendar()
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        inflate(context, R.layout.slycalendar_frame, this)
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.SlyCalendarView, defStyle, 0)
        if (slyCalendarData?.getBackgroundColor() == null) {
            slyCalendarData?.setBackgroundColor(
                typedArray.getColor(
                    R.styleable.SlyCalendarView_sly_backgroundColor, ContextCompat.getColor(
                        context, R.color.slycalendar_defBackgroundColor
                    )
                )
            )
        }
        if (slyCalendarData?.getHeaderColor() == null) {
            slyCalendarData?.setHeaderColor(
                typedArray.getColor(
                    R.styleable.SlyCalendarView_headerColor, ContextCompat.getColor(
                        context, R.color.slycalendar_defHeaderColor
                    )
                )
            )
        }
        if (slyCalendarData?.getHeaderTextColor() == null) {
            slyCalendarData?.setHeaderTextColor(
                typedArray.getColor(
                    R.styleable.SlyCalendarView_headerTextColor, ContextCompat.getColor(
                        context, R.color.slycalendar_defHeaderTextColor
                    )
                )
            )
        }
        if (slyCalendarData?.getTextColor() == null) {
            slyCalendarData?.setTextColor(
                typedArray.getColor(
                    R.styleable.SlyCalendarView_sly_textColor, ContextCompat.getColor(
                        context, R.color.slycalendar_defTextColor
                    )
                )
            )
        }
        if (slyCalendarData?.getSelectedColor() == null) {
            slyCalendarData?.setSelectedColor(
                typedArray.getColor(
                    R.styleable.SlyCalendarView_selectedColor, ContextCompat.getColor(
                        context, R.color.slycalendar_defSelectedColor
                    )
                )
            )
        }
        if (slyCalendarData?.getSelectedTextColor() == null) {
            slyCalendarData?.setSelectedTextColor(
                typedArray.getColor(
                    R.styleable.SlyCalendarView_selectedTextColor, ContextCompat.getColor(
                        context, R.color.slycalendar_defSelectedTextColor
                    )
                )
            )
        }
        typedArray.recycle()
        val vpager = findViewById<ViewPager>(R.id.content)
        vpager.adapter = MonthPagerAdapter(slyCalendarData!!, this)
        vpager.currentItem = vpager.adapter!!.count / 2
        showCalendar()
    }

    private fun showCalendar() {
        paintCalendar()
        showTime()
        findViewById<View>(R.id.txtCancel).setOnClickListener {
            if (callback != null) {
                callback!!.onCancelled()
            }
            if (completeListener != null) {
                completeListener?.complete()
            }
        }
        findViewById<View>(R.id.txtSave).setOnClickListener {
            if (callback != null) {
                var start: Calendar? = null
                var end: Calendar? = null
                if (slyCalendarData?.getSelectedStartDate() != null) {
                    start = Calendar.getInstance()
                    start.time = slyCalendarData?.getSelectedStartDate()!!
                }
                if (slyCalendarData?.getSelectedEndDate() != null) {
                    end = Calendar.getInstance()
                    end.time = slyCalendarData?.getSelectedEndDate()!!
                }
                callback!!.onDataSelected(
                    start,
                    end,
                    slyCalendarData!!.getSelectedHour(),
                    slyCalendarData!!.getSelectedMinutes()
                )
            }
            if (completeListener != null) {
                completeListener?.complete()
            }
        }
        val calendarStart = Calendar.getInstance()
        var calendarEnd: Calendar? = null
        if (slyCalendarData?.getSelectedStartDate() != null) {
            calendarStart.time = slyCalendarData?.getSelectedStartDate()!!
        } else {
            calendarStart.time = slyCalendarData?.getShowDate()!!
        }
        if (slyCalendarData?.getSelectedEndDate() != null) {
            calendarEnd = Calendar.getInstance()
            calendarEnd.time = slyCalendarData?.getSelectedEndDate()!!
        }
        (findViewById<View>(R.id.txtYear) as TextView).text =
            calendarStart[Calendar.YEAR].toString()
        if (calendarEnd == null) {
            (findViewById<View>(R.id.txtSelectedPeriod) as TextView).text =
                SimpleDateFormat("EE, dd MMMM", Locale.getDefault()).format(calendarStart.time)
        } else {
            if (calendarStart[Calendar.MONTH] == calendarEnd[Calendar.MONTH]) {
                (findViewById<View>(R.id.txtSelectedPeriod) as TextView).text =
                    context.getString(
                        R.string.slycalendar_dates_period,
                        SimpleDateFormat("EE, dd", Locale.getDefault()).format(calendarStart.time),
                        SimpleDateFormat("EE, dd MMM", Locale.getDefault()).format(calendarEnd.time)
                    )
            } else {
                (findViewById<View>(R.id.txtSelectedPeriod) as TextView).text =
                    context.getString(
                        R.string.slycalendar_dates_period,
                        SimpleDateFormat(
                            "EE, dd MMM",
                            Locale.getDefault()
                        ).format(calendarStart.time),
                        SimpleDateFormat("EE, dd MMM", Locale.getDefault()).format(calendarEnd.time)
                    )
            }
        }
        findViewById<View>(R.id.btnMonthPrev).setOnClickListener {
            val vpager = findViewById<ViewPager>(R.id.content)
            vpager.currentItem = vpager.currentItem - 1
        }
        findViewById<View>(R.id.btnMonthNext).setOnClickListener {
            val vpager = findViewById<ViewPager>(R.id.content)
            vpager.currentItem = vpager.currentItem + 1
        }
        findViewById<View>(R.id.txtTime).setOnClickListener {
            var style: Int = R.style.SlyCalendarTimeDialogTheme
            if (slyCalendarData?.getTimeTheme() != null) {
                style = slyCalendarData?.getTimeTheme()!!
            }
            val tpd = TimePickerDialog(
                context,
                style,
                { view, hourOfDay, minute ->
                    slyCalendarData?.setSelectedHour(hourOfDay)
                    slyCalendarData?.setSelectedMinutes(minute)
                    showTime()
                },
                slyCalendarData?.getSelectedHour()!!,
                slyCalendarData?.getSelectedMinutes()!!,
                true
            )
            tpd.show()
        }
        val vpager = findViewById<ViewPager>(R.id.content)
        vpager.adapter!!.notifyDataSetChanged()
        vpager.invalidate()
    }

    override fun dateSelect(selectedDate: Date) {
        if (slyCalendarData?.getSelectedStartDate() == null || slyCalendarData!!.isSingle()) {
            slyCalendarData?.setSelectedStartDate(selectedDate)
            showCalendar()
            return
        }
        if (slyCalendarData?.getSelectedEndDate() == null) {
            if (selectedDate.time < slyCalendarData?.getSelectedStartDate()!!.time) {
                slyCalendarData?.setSelectedEndDate(slyCalendarData?.getSelectedStartDate())
                slyCalendarData?.setSelectedStartDate(selectedDate)
                showCalendar()
                return
            } else if (selectedDate.time == slyCalendarData?.getSelectedStartDate()?.time) {
                slyCalendarData?.setSelectedEndDate(null)
                slyCalendarData?.setSelectedStartDate(selectedDate)
                showCalendar()
                return
            } else if (selectedDate.time > slyCalendarData?.getSelectedStartDate()?.time!!) {
                slyCalendarData?.setSelectedEndDate(selectedDate)
                showCalendar()
                return
            }
        }
        if (slyCalendarData?.getSelectedEndDate() != null) {
            slyCalendarData?.setSelectedEndDate(null)
            slyCalendarData?.setSelectedStartDate(selectedDate)
            showCalendar()
        }
    }

    override fun dateLongSelect(selectedDate: Date) {
        slyCalendarData?.setSelectedEndDate(null)
        slyCalendarData?.setSelectedStartDate(selectedDate)
        showCalendar()
    }

    private fun paintCalendar() {
        findViewById<View>(R.id.mainFrame).setBackgroundColor(slyCalendarData?.getBackgroundColor()!!)
        findViewById<View>(R.id.headerView).setBackgroundColor(slyCalendarData?.getHeaderColor()!!)
        (findViewById<View>(R.id.txtYear) as TextView).setTextColor(slyCalendarData?.getHeaderTextColor()!!)
        (findViewById<View>(R.id.txtSelectedPeriod) as TextView).setTextColor(slyCalendarData?.getHeaderTextColor()!!)
        (findViewById<View>(R.id.txtTime) as TextView).setTextColor(slyCalendarData?.getHeaderColor()!!)
    }

    private fun showTime() {
        val calendar = Calendar.getInstance()
        calendar[Calendar.HOUR_OF_DAY] = slyCalendarData?.getSelectedHour()!!
        calendar[Calendar.MINUTE] = slyCalendarData?.getSelectedMinutes()!!
        (findViewById<View>(R.id.txtTime) as TextView).text =
            SimpleDateFormat("HH:mm", Locale.getDefault()).format(calendar.time)
    }
}
