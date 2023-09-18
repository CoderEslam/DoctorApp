package com.doubleclick.doctorapp.android.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.doctorapp.android.Model.Clinic.ClinicPhone
import com.doubleclick.doctorapp.android.R
import com.doubleclick.doctorapp.android.ViewHolders.FavoriteDoctorsViewHolder
import com.doubleclick.doctorapp.android.ViewHolders.HelpCenterViewHolder
import com.doubleclick.doctorapp.android.ViewHolders.PhonesViewHolder

class PhonesAdapter(val clinic_phones: List<ClinicPhone>) :
    RecyclerView.Adapter<PhonesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhonesViewHolder {
        return PhonesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.layout_item_phone,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder: PhonesViewHolder, position: Int) {
        holder.phone.text = clinic_phones[holder.bindingAdapterPosition].phone
    }

    override fun getItemCount(): Int {
        return clinic_phones.size
    }
}