package com.doubleclick.doctorapp.android.Home.fragment

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Point
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import com.doubleclick.doctorapp.android.Adapters.BookingAdapter
import com.doubleclick.doctorapp.android.Model.PatientReservations.PatientReservationsModel
import com.doubleclick.doctorapp.android.databinding.FragmentLayouPatientBinding
import com.doubleclick.doctorapp.android.databinding.MyQrCodeBinding
import com.doubleclick.doctorapp.android.views.qrgenearator.QRGContents
import com.doubleclick.doctorapp.android.views.qrgenearator.QRGEncoder
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomDialogPatiant(val patientReservationsModel: List<PatientReservationsModel>) :
    BottomSheetDialogFragment() {

    private lateinit var binding: FragmentLayouPatientBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLayouPatientBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvBooking.adapter = BookingAdapter(patientReservationsModel)

    }

}
