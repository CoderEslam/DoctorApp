package com.doubleclick.doctorapp.android.Model.Auth

data class RegistrationDoctor(
    val name: String,
    val email: String,
    val phone: String,
    val password: String,
    val password_confirmation: String
)
