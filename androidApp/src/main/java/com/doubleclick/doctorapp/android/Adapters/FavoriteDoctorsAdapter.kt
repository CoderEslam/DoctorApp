package com.doubleclick.doctorapp.android.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.doubleclick.doctorapp.android.BookingTime
import com.doubleclick.doctorapp.android.FavoritesDoctor
import com.doubleclick.doctorapp.android.Model.Favorite.FavoriteModel
import com.doubleclick.doctorapp.android.R
import com.doubleclick.doctorapp.android.ViewHolders.FavoriteDoctorsViewHolder
import com.doubleclick.doctorapp.android.utils.Constants.IMAGE_URL

class FavoriteDoctorsAdapter(
    val favoriteModel: List<FavoriteModel>,
    val favorites: FavoritesDoctor,
    val booking: BookingTime
) :
    RecyclerView.Adapter<FavoriteDoctorsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteDoctorsViewHolder {
        return FavoriteDoctorsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.favorite_layout,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder: FavoriteDoctorsViewHolder, position: Int) {
        holder.favorite_icon.setOnClickListener {
            favorites.deleteFavorite(favoriteModel[holder.bindingAdapterPosition].id.toString())
        }
        holder.booking.setOnClickListener {
            booking.book()
        }
        try {
            holder.name_doctor.text = favoriteModel[holder.bindingAdapterPosition].user.name
            holder.specializations.text =
                favoriteModel[holder.bindingAdapterPosition].doctor.specialization_id.toString()
            holder.genaral_specalization.text =
                favoriteModel[holder.bindingAdapterPosition].doctor.general_specialty_id.toString()
        }catch (_:NullPointerException){}
        Glide.with(holder.itemView.context).apply {
            load(IMAGE_URL + favoriteModel[holder.bindingAdapterPosition].user.user_image).apply {
                placeholder(ContextCompat.getDrawable(holder.itemView.context, R.drawable.girl))
                into(holder.image_doctor)
            }
        }

    }

    override fun getItemCount(): Int = favoriteModel.size;

}