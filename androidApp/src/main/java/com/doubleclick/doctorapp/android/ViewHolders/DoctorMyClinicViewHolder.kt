package com.doubleclick.doctorapp.android.ViewHolders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.doctorapp.android.R

class DoctorMyClinicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val name: TextView = itemView.findViewById(R.id.name);
    val address: TextView = itemView.findViewById(R.id.address);
    val opening_time: TextView = itemView.findViewById(R.id.opening_time);
    val price: TextView = itemView.findViewById(R.id.price);
    val rv_phones: RecyclerView = itemView.findViewById(R.id.rv_phones);
}