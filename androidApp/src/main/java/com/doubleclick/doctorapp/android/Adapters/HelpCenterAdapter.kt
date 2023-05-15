package com.doubleclick.doctorapp.android.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.doctorapp.android.R
import com.doubleclick.doctorapp.android.ViewHolders.FavoriteDoctorsViewHolder
import com.doubleclick.doctorapp.android.ViewHolders.HelpCenterViewHolder

class HelpCenterAdapter() : RecyclerView.Adapter<HelpCenterViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HelpCenterViewHolder {
        return HelpCenterViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.help_center_layout,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder: HelpCenterViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 100
    }
}