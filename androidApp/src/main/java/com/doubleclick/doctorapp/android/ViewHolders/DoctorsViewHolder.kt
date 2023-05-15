package com.doubleclick.doctorapp.android.ViewHolders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.doctorapp.android.R

class DoctorsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val doctor_image: ImageView = itemView.findViewById(R.id.doctor_image);
    val doctor_name: TextView = itemView.findViewById(R.id.doctor_name);
    val general_specialie: TextView = itemView.findViewById(R.id.general_specialie);


}