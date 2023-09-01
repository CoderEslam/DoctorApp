package com.doubleclick.doctorapp.android.Adapters

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.doubleclick.doctorapp.android.Home.DoctorVideoActivity
import com.doubleclick.doctorapp.android.Model.MedicalAdvice.MedicalAdviceModel
import com.doubleclick.doctorapp.android.R
import com.doubleclick.doctorapp.android.ViewHolders.DoctorVideoViewHolder
import com.doubleclick.doctorapp.android.utils.Constants.IMAGE_URL_MEDICAL_ADVICES_VIDEOS

class DoctorVideoAdapter(
    val medicalAdviceModelList: MutableList<MedicalAdviceModel>
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
            medicalAdviceModelList[0] = medicalAdviceModelList[holder.bindingAdapterPosition]
            holder.itemView.context.startActivity(
                Intent(
                    holder.itemView.context,
                    DoctorVideoActivity::class.java
                ).putParcelableArrayListExtra(
                    "medicalAdviceModelList",
                    medicalAdviceModelList as java.util.ArrayList<out Parcelable>
                )
            )
        }

        // reference -> https://www.tutorialspoint.com/how-does-one-use-glide-to-download-an-image-into-a-bitmap
        Glide.with(holder.itemView.context).asDrawable()
            .load(IMAGE_URL_MEDICAL_ADVICES_VIDEOS + medicalAdviceModelList[holder.bindingAdapterPosition].video_name)
            .into(object : CustomTarget<Drawable>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    holder.image.setImageDrawable(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }

            })

        holder.video_view_doctor.setMedia(Uri.parse(IMAGE_URL_MEDICAL_ADVICES_VIDEOS + medicalAdviceModelList[holder.bindingAdapterPosition].video_name))
        /*
        Glide.with(holder.itemView)
            .load(IMAGE_URL_MEDICAL_ADVICES_VIDEOS + medicalAdviceModelList[holder.bindingAdapterPosition].video_name)
            .into(holder.video_view_doctor)
            */
    }

    override fun getItemCount(): Int {
        return medicalAdviceModelList.size
    }
}