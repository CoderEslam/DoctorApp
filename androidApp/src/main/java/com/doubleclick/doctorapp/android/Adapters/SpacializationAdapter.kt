package com.doubleclick.doctorapp.android.Adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.doctorapp.android.Home.Calender.CalendarViewActivity
import com.doubleclick.doctorapp.android.Home.Filter.FilterActivity
import com.doubleclick.doctorapp.android.Home.Live.DoctorLiveActivity
import com.doubleclick.doctorapp.android.Home.PatientReservation.PatientReservationActivity
import com.doubleclick.doctorapp.android.Model.Specialization.SpecializationModel
import com.doubleclick.doctorapp.android.R
import com.doubleclick.doctorapp.android.ViewHolders.SpacificationViewHolder

class SpacializationAdapter(val specializationModel: List<SpecializationModel>) :
    RecyclerView.Adapter<SpacificationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpacificationViewHolder {
        return SpacificationViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_spacification, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SpacificationViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            Toast.makeText(
                holder.itemView.context,
                specializationModel[holder.bindingAdapterPosition].name,
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    override fun getItemCount(): Int {
        return specializationModel.size
    }
}