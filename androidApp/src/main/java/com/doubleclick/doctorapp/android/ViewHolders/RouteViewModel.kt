package com.doubleclick.doctorapp.android.ViewHolders

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.doctorapp.android.R

class RouteViewModel(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val name: TextView = itemView.findViewById(R.id.name)
    val constrain_parent:ConstraintLayout = itemView.findViewById(R.id.constrain_parent);

}