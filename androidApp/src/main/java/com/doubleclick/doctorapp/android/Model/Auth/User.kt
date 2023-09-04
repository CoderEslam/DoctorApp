package com.doubleclick.doctorapp.android.Model.Auth

data class User(
    val device_token: String,
    val email: String,
    val id: Int,
    val name: String,
    val phone: String,
    val status: String,
    val user_image: String
)