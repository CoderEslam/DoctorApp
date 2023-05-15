package com.doubleclick.doctorapp.android.ViewHolders

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.doctorapp.android.R

class NavigationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val icon: ImageView = itemView.findViewById(R.id.icon);
    val name: TextView = itemView.findViewById(R.id.name);
    val root_linear_layout: LinearLayout = itemView.findViewById(R.id.root_linear_layout);
}