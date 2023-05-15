package com.doubleclick.doctorapp.android.Model.Days

data class DaysAtClinicModel(
    val clinic_id: Int,
    val day_id: Int,
    val doctor_id: Int,
    val from_time: String,
    val id: Int,
    val to_time: String,
    val user_id: Int
)