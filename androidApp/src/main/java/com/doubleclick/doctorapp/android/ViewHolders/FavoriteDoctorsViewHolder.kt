package com.doubleclick.doctorapp.android.ViewHolders

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.doctorapp.android.R

class FavoriteDoctorsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val image_doctor: ImageView = itemView.findViewById(R.id.image_doctor);
    val name_doctor: TextView = itemView.findViewById(R.id.name_doctor);
    val specializations: TextView = itemView.findViewById(R.id.specializations);
    val genaral_specalization: TextView = itemView.findViewById(R.id.genaral_specalization);
    val booking: Button = itemView.findViewById(R.id.booking);
    val favorite_icon: ImageView = itemView.findViewById(R.id.favorite_icon)

}