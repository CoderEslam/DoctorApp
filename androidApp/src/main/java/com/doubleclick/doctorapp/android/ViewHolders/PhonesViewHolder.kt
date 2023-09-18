package com.doubleclick.doctorapp.android.ViewHolders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.doctorapp.android.R

class PhonesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val phone: TextView = itemView.findViewById(R.id.phone)
}