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

        holder.patent_name.text = buildString {
            append(if (patientReservationsModel[holder.bindingAdapterPosition].patient?.name != null) "${patientReservationsModel[holder.bindingAdapterPosition].patient?.name}" else "--")
        }
        holder.patent_phone.text = buildString {
            append(if (patientReservationsModel[holder.bindingAdapterPosition].patient_phone != null) "${patientReservationsModel[holder.bindingAdapterPosition].patient_phone}" else "--")
        }
        holder.reservation_time.text = buildString {
            append(if (patientReservationsModel[holder.bindingAdapterPosition].reservation_date != null) "${patientReservationsModel[holder.bindingAdapterPosition].reservation_date}" else "--")
        }
        holder.doctor_name.text = buildString {
            append(if (patientReservationsModel[holder.bindingAdapterPosition].doctor?.name != null) "${patientReservationsModel[holder.bindingAdapterPosition].doctor?.name}" else "--")
        }
        holder.address.text = buildString {
            append(if (patientReservationsModel[holder.bindingAdapterPosition].clinic?.address != null) "${patientReservationsModel[holder.bindingAdapterPosition].clinic?.address}" else "--")
        }

    }

    override fun getItemCount(): Int = patientReservationsModel.size
}