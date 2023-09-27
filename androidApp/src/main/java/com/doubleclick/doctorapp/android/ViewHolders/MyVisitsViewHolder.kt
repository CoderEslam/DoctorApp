package com.doubleclick.doctorapp.android.ViewHolders

import android.view.View
import android.widget.StackView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.doctorapp.android.R
import org.w3c.dom.Text

class MyVisitsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val stack_view_image: StackView = itemView.findViewById(R.id.stack_view_image)
    val doctor_name: TextView = itemView.findViewById(R.id.doctor_name)
    val clinic_address: TextView = itemView.findViewById(R.id.address)
    val clinic_name: TextView = itemView.findViewById(R.id.address)

}