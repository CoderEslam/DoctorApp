package com.doubleclick.doctorapp.android.Adapters

import android.content.Intent
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.doubleclick.doctorapp.android.Home.DoctorVideoActivity
import com.doubleclick.doctorapp.android.Model.MedicalAdvice.MedicalAdviceModel
import com.doubleclick.doctorapp.android.R
import com.doubleclick.doctorapp.android.ViewHolders.DoctorVideoViewHolder
import com.doubleclick.doctorapp.android.utils.Constants.IMAGE_URL_MEDICAL_ADVICES_VIDEOS

class DoctorVideoAdapter(
    val medicalAdviceModelList: List<MedicalAdviceModel>
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
            holder.itemView.context.startActivity(
                Intent(
                    holder.itemView.context,
                    DoctorVideoActivity::class.java
                ).putParcelableArrayListExtra("medicalAdviceModelList", medicalAdviceModelList as java.util.ArrayList<out Parcelable>)
            )
        }

        Glide.with(holder.itemView)
            .load(IMAGE_URL_MEDICAL_ADVICES_VIDEOS + medicalAdviceModelList?.get(holder.bindingAdapterPosition)?.video_name)
            .into(holder.video_view_doctor)
    }

    override fun getItemCount(): Int {
        return medicalAdviceModelList?.size ?: 0;
    }
}