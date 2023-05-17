package com.doubleclick.doctorapp.android.Model.PatientReservations

data class Clinic(
    val address: String,
    val area_id: Int,
    val closing_time: String,
    val doctor_id: Int,
    val governorate_id: Int,
    val id: Int,
    val name: String,
    val notes: String,
    val opening_time: String,
    val overview: String,
    val price: String,
    val reservation_time_end: String,
    val reservation_time_start: String,
    val status: String,
    val user_id: Int
)