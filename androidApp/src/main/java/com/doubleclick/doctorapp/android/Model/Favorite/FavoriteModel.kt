package com.doubleclick.doctorapp.android.Model.Favorite

import com.doubleclick.doctorapp.android.Model.Doctor.DoctorModel
import com.doubleclick.doctorapp.android.Model.User.UserModel

data class FavoriteModel(
    val doctor: DoctorModel,
    val doctor_id: Int,
    val id: Int,
    val user: UserModel,
    val user_id: Int
)