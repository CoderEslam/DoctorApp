package com.doubleclick.doctorapp.android.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.doctorapp.android.Model.MedicalAdvice.MedicalAdviceModel
import com.doubleclick.doctorapp.android.R
import com.doubleclick.doctorapp.android.ViewHolders.DoctorVideoViewHolder

class DoctorVideoAdapter(
    val medicalAdviceModelList: List<MedicalAdviceModel>?
) : RecyclerView.Adapter<DoctorVideoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorVideoViewHolder {
        return DoctorVideoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_video, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: DoctorVideoViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            Toast.makeText(
                holder.itemView.context,
                "${holder.bindingAdapterPosition}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun getItemCount(): Int {
        return medicalAdviceModelList?.size ?: 0;
    }
}