package com.doubleclick.doctorapp.android.Adapters

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.media3.common.Player.REPEAT_MODE_ALL
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.doubleclick.doctorapp.android.Model.MedicalAdvice.MedicalAdviceModel
import com.doubleclick.doctorapp.android.R
import com.doubleclick.doctorapp.android.ViewHolders.DoctorVideoViewPagerViewHolder
import com.doubleclick.doctorapp.android.utils.Constants.IMAGE_URL_MEDICAL_ADVICES_VIDEOS
import com.doubleclick.doctorapp.android.utils.Constants.IMAGE_URL_USERS
import com.doubleclick.doctorapp.android.views.videoView.listener.OnCompletionListener

class DoctorVideoViewPagerAdapter(
    val medicalAdviceModelList: List<MedicalAdviceModel>
) : RecyclerView.Adapter<DoctorVideoViewPagerViewHolder>() {

    private val TAG = "DoctorVideoViewPagerAda"

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): DoctorVideoViewPagerViewHolder {
        return DoctorVideoViewPagerViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_video_view_pager, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: DoctorVideoViewPagerViewHolder, position: Int) {
        holder.video_.setMedia(
            Uri.parse(
                IMAGE_URL_MEDICAL_ADVICES_VIDEOS + medicalAdviceModelList[holder.bindingAdapterPosition].video_name
            )
        )
        holder.video_.setRepeatMode(REPEAT_MODE_ALL)
        Log.e(TAG, "onBindViewHolder: ${holder.bindingAdapterPosition}")

        holder.video_.start()
        holder.name_doctor.text = medicalAdviceModelList[holder.bindingAdapterPosition].doctor?.name
        Glide.with(holder.itemView)
            .load(IMAGE_URL_USERS + medicalAdviceModelList[holder.bindingAdapterPosition].user?.user_image)
            .placeholder(R.drawable.logo).into(holder.image_doctor)
    }

    override fun getItemCount(): Int {
        return medicalAdviceModelList.size
    }
}