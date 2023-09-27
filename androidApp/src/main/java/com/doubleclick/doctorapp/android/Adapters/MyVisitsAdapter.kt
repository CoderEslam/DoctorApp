package com.doubleclick.doctorapp.android.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.doctorapp.android.Model.Patient.MyVisits.MyVisitsModel
import com.doubleclick.doctorapp.android.Model.PatientReservations.PatientReservationsModel
import com.doubleclick.doctorapp.android.R
import com.doubleclick.doctorapp.android.ViewHolders.BookingViewHolder
import com.doubleclick.doctorapp.android.ViewHolders.MyVisitsViewHolder
import com.doubleclick.doctorapp.android.utils.isNotNullOrEmptyString

class MyVisitsAdapter(val patientMyVisitsModel: List<MyVisitsModel>) :
    RecyclerView.Adapter<MyVisitsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyVisitsViewHolder =
        MyVisitsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_item_patient_my_visits, parent, false)
        )

    override fun onBindViewHolder(holder: MyVisitsViewHolder, position: Int) {
        var images = listOf<String>()
        if (patientMyVisitsModel[holder.bindingAdapterPosition].images?.isNotNullOrEmptyString() == true) {
            images =
                patientMyVisitsModel[holder.bindingAdapterPosition].images?.split(",")?.toList()!!
        }
        holder.stack_view_image.adapter = StackViewAdapter(holder.itemView.context, images)

        holder.doctor_name.text = buildString {
            append(if (patientMyVisitsModel[holder.bindingAdapterPosition].doctor?.name != null) "${patientMyVisitsModel[holder.bindingAdapterPosition].doctor?.name}" else "--")
        }

        holder.clinic_address.text = buildString {
            append(if (patientMyVisitsModel[holder.bindingAdapterPosition].clinic?.address != null) "${patientMyVisitsModel[holder.bindingAdapterPosition].clinic?.address}" else "--")
        }

        holder.clinic_name.text = buildString {
            append(if (patientMyVisitsModel[holder.bindingAdapterPosition].clinic?.name != null) "${patientMyVisitsModel[holder.bindingAdapterPosition].clinic?.name}" else "--")
        }

    }

    override fun getItemCount(): Int = patientMyVisitsModel.size
}