package com.doubleclick.doctorapp.android.ViewHolders

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.doctorapp.android.R

class FamilyMemberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val name: TextView = itemView.findViewById(R.id.name);
    val smoking: TextView = itemView.findViewById(R.id.smoking);
    val materiel_status: TextView = itemView.findViewById(R.id.materiel_status);
    val phone: TextView = itemView.findViewById(R.id.phone);
    val blood_type: TextView = itemView.findViewById(R.id.blood_type);
    val more: ImageView = itemView.findViewById(R.id.more);
}