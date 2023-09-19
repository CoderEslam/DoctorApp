package com.doubleclick.doctorapp.android

import com.doubleclick.doctorapp.android.Model.Favorite.FavoriteModel

interface BookingTime {
    fun book(favoriteModel: FavoriteModel)
}