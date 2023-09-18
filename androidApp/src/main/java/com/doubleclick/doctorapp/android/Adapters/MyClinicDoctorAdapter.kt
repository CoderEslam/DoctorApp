package com.doubleclick.doctorapp.android.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.doctorapp.android.Home.PatientReservation.PatientReservationActivity
import com.doubleclick.doctorapp.android.Model.Clinic.ClinicList
import com.doubleclick.doctorapp.android.Model.Clinic.ClinicModel
import com.doubleclick.doctorapp.android.R
import com.doubleclick.doctorapp.android.ViewHolders.DoctorClinicViewHolder
import com.doubleclick.doctorapp.android.ViewHolders.DoctorMyClinicViewHolder

class MyClinicDoctorAdapter(val clinicModel: List<ClinicModel>) :
    RecyclerView.Adapter<DoctorMyClinicViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorMyClinicViewHolder {
        return DoctorMyClinicViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.layout_item_clinic,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder: DoctorMyClinicViewHolder, position: Int) {

        holder.name.text = clinicModel[holder.bindingAdapterPosition].name.toString()
        holder.address.text = buildString {
            append(clinicModel[holder.bindingAdapterPosition].address.toString())
            append(", ")
            append(clinicModel[holder.bindingAdapterPosition].governorate?.name.toString())
            append(", ")
            append(clinicModel[holder.bindingAdapterPosition].area?.name.toString())
        }
        holder.opening_time.text =
            clinicModel[holder.bindingAdapterPosition].opening_time.toString()
        holder.price.text = clinicModel[holder.bindingAdapterPosition].price.toString()
        if (clinicModel[holder.bindingAdapterPosition].clinic_phones != null) {
            holder.rv_phones.adapter =
                PhonesAdapter(clinicModel[holder.bindingAdapterPosition].clinic_phones!!)
        }
    }

    override fun getItemCount(): Int {
        return clinicModel.size
    }
}