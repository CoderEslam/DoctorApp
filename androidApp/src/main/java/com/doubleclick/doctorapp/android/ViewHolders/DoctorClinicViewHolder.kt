package com.doubleclick.doctorapp.android.ViewHolders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.doctorapp.android.R

class DoctorClinicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val clinic_name: TextView = itemView.findViewById(R.id.clinic_name);
    val address: TextView = itemView.findViewById(R.id.address);
    val opening_time: TextView = itemView.findViewById(R.id.opening_time);
    val close_time: TextView = itemView.findViewById(R.id.close_time);
}