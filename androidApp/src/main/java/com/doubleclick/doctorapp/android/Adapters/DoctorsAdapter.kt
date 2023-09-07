package com.doubleclick.doctorapp.android.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.doubleclick.doctorapp.android.Home.DoctorDetails.DoctorDetailsActivity
import com.doubleclick.doctorapp.android.Model.Doctor.DoctorData
import com.doubleclick.doctorapp.android.R
import com.doubleclick.doctorapp.android.ViewHolders.DoctorsViewHolder
import com.doubleclick.doctorapp.android.utils.Constants.IMAGE_URL_USERS

class DoctorsAdapter(val doctorData: List<DoctorData>) : RecyclerView.Adapter<DoctorsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorsViewHolder {
        return DoctorsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_doctor, parent, false)
        )
    }

    override fun onBindViewHolder(holder: DoctorsViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(IMAGE_URL_USERS + doctorData[holder.bindingAdapterPosition].user?.user_image)
            .into(holder.doctor_image)
        holder.doctor_name.text = doctorData[holder.bindingAdapterPosition].name
        holder.doctor_name.isSelected = true
        holder.general_specialie.text =
            doctorData[holder.bindingAdapterPosition].general_specialty?.name
        holder.itemView.setOnClickListener {
            val intent = Intent(
                holder.itemView.context,
                DoctorDetailsActivity::class.java
            )
            intent.putExtra("doctor_id", doctorData[holder.bindingAdapterPosition].id.toString())
            holder.itemView.context.startActivity(intent)
        }

    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun getItemCount(): Int {
        return doctorData.size
    }
}