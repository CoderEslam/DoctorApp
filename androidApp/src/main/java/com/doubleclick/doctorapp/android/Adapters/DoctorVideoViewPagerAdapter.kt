package com.doubleclick.doctorapp.android.Adapters

import android.content.Intent
import android.net.Uri
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
import com.doubleclick.doctorapp.android.ViewHolders.DoctorVideoViewPagerViewHolder
import com.doubleclick.doctorapp.android.utils.Constants.IMAGE_URL_MEDICAL_ADVICES_VIDEOS

class DoctorVideoViewPagerAdapter(
    val medicalAdviceModelList: List<MedicalAdviceModel>
) : RecyclerView.Adapter<DoctorVideoViewPagerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorVideoViewPagerViewHolder {
        return DoctorVideoViewPagerViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_video_view_pager, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: DoctorVideoViewPagerViewHolder, position: Int) {
        holder.video_.setMedia(
            Uri.parse(
                IMAGE_URL_MEDICAL_ADVICES_VIDEOS + medicalAdviceModelList?.get(
                    holder.bindingAdapterPosition
                )?.video_name
            )
        )
        holder.video_.stop()

        Glide.with(holder.itemView)
            .load(IMAGE_URL_MEDICAL_ADVICES_VIDEOS + medicalAdviceModelList?.get(holder.bindingAdapterPosition)?.video_name)
            .placeholder(R.drawable.logo)
            .into(holder.video_view_doctor)
    }

    override fun getItemCount(): Int {
        return medicalAdviceModelList.size
    }
}