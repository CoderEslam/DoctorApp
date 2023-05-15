package com.doubleclick.doctorapp.android.Home.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.doubleclick.doctorapp.android.R
import com.doubleclick.doctorapp.android.databinding.FragmentBookBinding
import com.doubleclick.doctorapp.android.views.slycalendarview.SlyCalendarDialog
import java.text.SimpleDateFormat
import java.util.*

class BookFragment : Fragment(), SlyCalendarDialog.Callback {


    private lateinit var binding: FragmentBookBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dilog = SlyCalendarDialog()
            .setSingle(false)
            .setFirstMonday(false)
            .setCallback(this)
            .setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
            .setHeaderColor(ContextCompat.getColor(requireContext(), R.color.green))
            .setSelectedTextColor(ContextCompat.getColor(requireContext(), R.color.grey_text))
            .setTextColor(ContextCompat.getColor(requireContext(), R.color.grey_700))
            .setSelectedColor(ContextCompat.getColor(requireContext(), R.color.green))
//            .show(requireActivity().supportFragmentManager, "SLY CALENDAR")

        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.calender_view, dilog)
            .commit()
    }

    override fun onCancelled() {
        //Nothing
    }

    override fun onDataSelected(
        firstDate: Calendar?,
        secondDate: Calendar?,
        hours: Int,
        minutes: Int
    ) {
        if (firstDate != null) {
            if (secondDate == null) {
                firstDate[Calendar.HOUR_OF_DAY] = hours
                firstDate[Calendar.MINUTE] = minutes
                Toast.makeText(
                    requireContext(),
                    SimpleDateFormat(getString(R.string.timeFormat), Locale.getDefault()).format(
                        firstDate.time
                    ),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(
                        R.string.period,
                        SimpleDateFormat(
                            getString(R.string.dateFormat),
                            Locale.getDefault()
                        ).format(firstDate.time),
                        SimpleDateFormat(
                            getString(R.string.timeFormat),
                            Locale.getDefault()
                        ).format(secondDate.time)
                    ),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }


}