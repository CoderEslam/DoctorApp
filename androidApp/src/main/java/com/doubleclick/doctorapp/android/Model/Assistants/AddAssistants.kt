package com.doubleclick.doctorapp.android.Model.Assistants

data class AddAssistants(
    val name: String,
    val email: String,
    val password: String,
    val password_confirmation: String,
    val status: String = "Active",
    val phone: String,
    val doctor_id: String,
    val clinic_id: String
)
