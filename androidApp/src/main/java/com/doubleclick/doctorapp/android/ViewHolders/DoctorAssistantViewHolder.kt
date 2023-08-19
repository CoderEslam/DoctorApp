package com.doubleclick.doctorapp.android.ViewHolders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.doctorapp.android.R

class DoctorAssistantViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    val assistant_name:TextView = itemView.findViewById(R.id.assistant_name)
    val assistant_phone:TextView = itemView.findViewById(R.id.assistant_phone)
    val delete_assistant:ImageView = itemView.findViewById(R.id.delete_assistant)

}