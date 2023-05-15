package com.doubleclick.doctorapp.android.views.slycalendarview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.doubleclick.doctorapp.android.R
import com.doubleclick.doctorapp.android.databinding.SlycalendarMainBinding
import com.doubleclick.doctorapp.android.views.slycalendarview.listeners.DialogCompleteListener
import com.doubleclick.doctorapp.android.views.superbottomsheet.SuperBottomSheetDialog
import com.doubleclick.doctorapp.android.views.superbottomsheet.SuperBottomSheetFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*

class SlyCalendarDialog : BottomSheetDialogFragment(),
    DialogCompleteListener {
    private val slyCalendarData = SlyCalendarData()
    private var callback: Callback? = null
    private lateinit var binding: SlycalendarMainBinding
    fun setStartDate(startDate: Date?): SlyCalendarDialog {
        slyCalendarData.setSelectedStartDate(startDate)
        return this
    }

    fun setEndDate(endDate: Date?): SlyCalendarDialog {
        slyCalendarData.setSelectedEndDate(endDate)
        return this
    }

    fun setSingle(single: Boolean): SlyCalendarDialog {
        slyCalendarData.setSingle(single)
        return this
    }

    fun setFirstMonday(firsMonday: Boolean): SlyCalendarDialog {
        slyCalendarData.setFirstMonday(firsMonday)
        return this
    }

    fun setCallback(callback: Callback?): SlyCalendarDialog {
        this.callback = callback
        return this
    }

    fun setTimeTheme(themeResource: Int?): SlyCalendarDialog {
        slyCalendarData.setTimeTheme(themeResource)
        return this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.SlyCalendarDialogStyle)
    }

    val calendarFirstDate: Date?
        get() = slyCalendarData.getSelectedStartDate()
    val calendarSecondDate: Date?
        get() = slyCalendarData.getSelectedEndDate()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = SlycalendarMainBinding.inflate(inflater, container, false)
        binding.slyCalendar.setSlyCalendarData(slyCalendarData)
        binding.slyCalendar.setCallback(callback)
        binding.slyCalendar.setCompleteListener(this)
        return binding.root
    }

    override fun complete() {
//        dismiss()
    }

    interface Callback {
        fun onCancelled()
        fun onDataSelected(firstDate: Calendar?, secondDate: Calendar?, hours: Int, minutes: Int)
    }

    fun setBackgroundColor(backgroundColor: Int?): SlyCalendarDialog {
        slyCalendarData.setBackgroundColor(backgroundColor)
        return this
    }

    fun setHeaderColor(headerColor: Int?): SlyCalendarDialog {
        slyCalendarData.setHeaderColor(headerColor)
        return this
    }

    fun setHeaderTextColor(headerTextColor: Int?): SlyCalendarDialog {
        slyCalendarData.setHeaderTextColor(headerTextColor)
        return this
    }

    fun setTextColor(textColor: Int?): SlyCalendarDialog {
        slyCalendarData.setTextColor(textColor)
        return this
    }

    fun setSelectedColor(selectedColor: Int?): SlyCalendarDialog {
        slyCalendarData.setSelectedColor(selectedColor)
        return this
    }

    fun setSelectedTextColor(selectedTextColor: Int?): SlyCalendarDialog {
        slyCalendarData.setSelectedTextColor(selectedTextColor)
        return this
    }
}
