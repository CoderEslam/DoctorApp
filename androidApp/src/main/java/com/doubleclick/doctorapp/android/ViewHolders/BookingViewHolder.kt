package com.doubleclick.doctorapp.android.ViewHolders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.doctorapp.android.R
import org.w3c.dom.Text

class BookingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val patent_name: TextView = itemView.findViewById(R.id.patent_name)
    val patent_phone: TextView = itemView.findViewById(R.id.patent_phone)
    val reservation_time: TextView = itemView.findViewById(R.id.reservation_time)
    val doctor_name: TextView = itemView.findViewById(R.id.doctor_name)
    val address: TextView = itemView.findViewById(R.id.address)

}