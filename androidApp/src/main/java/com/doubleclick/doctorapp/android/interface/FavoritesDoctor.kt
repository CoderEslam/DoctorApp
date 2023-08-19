package com.doubleclick.doctorapp.android

import com.doubleclick.doctorapp.android.Model.Favorite.FavoriteModel

interface FavoritesDoctor {
    fun deleteFavorite(favoriteModel: FavoriteModel, id: String)
    fun setFavorite(id: String)
}