package com.doubleclick.doctorapp.android.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.doctorapp.android.Home.PatientReservation.PatientReservationActivity
import com.doubleclick.doctorapp.android.Model.Clinic.ClinicData
import com.doubleclick.doctorapp.android.R
import com.doubleclick.doctorapp.android.ViewHolders.DoctorClinicViewHolder

class DoctorClinicAdapter(val clinicData: List<ClinicData>) :
    RecyclerView.Adapter<DoctorClinicViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorClinicViewHolder {
        return DoctorClinicViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.layout_clinic_item,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder: DoctorClinicViewHolder, position: Int) {
        holder.clinic_name.text = clinicData[holder.bindingAdapterPosition].name
        holder.address.text = clinicData[holder.bindingAdapterPosition].address
        holder.opening_time.text =
            "Opening time ${clinicData[holder.bindingAdapterPosition].opening_time}"
        holder.close_time.text =
            "CLose time ${clinicData[holder.bindingAdapterPosition].closing_time}"
        holder.itemView.setOnClickListener {
            val intent = Intent(
                holder.itemView.context,
                PatientReservationActivity::class.java
            )
            intent.putExtra("clinic_id", clinicData[holder.bindingAdapterPosition].id.toString())
            intent.putExtra("doctor_id", clinicData[holder.bindingAdapterPosition].doctor_id.toString())
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return clinicData.size
    }
}