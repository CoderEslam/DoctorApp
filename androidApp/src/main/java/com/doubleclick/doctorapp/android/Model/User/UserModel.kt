package com.doubleclick.doctorapp.android.Model.User

data class UserModel(
    val device_token: String,
    val email: String,
    val id: Int,
    val name: String,
    val phone: String,
    val roles: List<Role>,
    val status: String,
    val user_image: String
)