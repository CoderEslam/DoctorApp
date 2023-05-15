package com.doubleclick.doctorapp.android.Model.Assistants

data class AddAssistants(
    val name: String,
    val status: String = "Active",
    val phone: String,
    val doctor_id: String
)
