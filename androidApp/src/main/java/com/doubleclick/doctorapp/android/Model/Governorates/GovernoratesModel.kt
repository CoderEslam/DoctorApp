package com.doubleclick.doctorapp.android.Model.Governorates

import com.doubleclick.doctorapp.android.Model.User.UserModel

data class GovernoratesModel(
    val governorate: Governorate,
    val governorate_id: Int,
    val id: Int,
    val name: String,
    val status: String,
    val user: UserModel,
    val user_id: Int
)