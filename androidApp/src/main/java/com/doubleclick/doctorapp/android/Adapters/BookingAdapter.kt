package com.doubleclick.doctorapp.android.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.doctorapp.android.Model.PatientReservations.PatientReservationsModel
import com.doubleclick.doctorapp.android.R
import com.doubleclick.doctorapp.android.ViewHolders.BookingViewHolder

class BookingAdapter(val patientReservationsModel: List<PatientReservationsModel>) :
    RecyclerView.Adapter<BookingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder =
        BookingViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_item_patient_reservation, parent, false)
        )

    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = patientReservationsModel.size
}