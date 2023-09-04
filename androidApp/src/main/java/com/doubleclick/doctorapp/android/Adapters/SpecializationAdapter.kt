package com.doubleclick.doctorapp.android.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.doubleclick.doctorapp.android.Model.Specialization.SpecializationModel
import com.doubleclick.doctorapp.android.R
import com.doubleclick.doctorapp.android.Search
import com.doubleclick.doctorapp.android.ViewHolders.SpacificationViewHolder
import com.doubleclick.doctorapp.android.utils.Constants.IMAGE_URL_SPECIALIZATIONS

class SpecializationAdapter(
    val search: Search,
    val specializationModel: List<SpecializationModel>
) :
    RecyclerView.Adapter<SpacificationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpacificationViewHolder {
        return SpacificationViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_spacification, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SpacificationViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(IMAGE_URL_SPECIALIZATIONS + specializationModel[holder.bindingAdapterPosition].specialization_image)
            .placeholder(R.drawable.teeth)
            .into(holder.image)

        holder.itemView.setOnClickListener {
            search.search(specializationModel[holder.bindingAdapterPosition].name.toString())
        }
    }


    override fun getItemCount(): Int {
        return specializationModel.size
    }
}