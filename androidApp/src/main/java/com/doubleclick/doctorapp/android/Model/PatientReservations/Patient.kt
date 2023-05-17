package com.doubleclick.doctorapp.android.Model.PatientReservations

data class Patient(
    val area_id: Int,
    val governorate_id: Int,
    val id: Int,
    val name: String,
    val notes: String,
    val phone: String,
    val status: String,
    val telephone: String,
    val user_id: Int
)