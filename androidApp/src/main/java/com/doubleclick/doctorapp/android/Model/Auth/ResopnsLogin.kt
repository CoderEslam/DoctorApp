package com.doubleclick.doctorapp.android.Model.Auth

import com.doubleclick.doctorapp.android.Model.User.UserModel


data class ResopnsLogin(
    val role: String,
    val user: UserModel
)