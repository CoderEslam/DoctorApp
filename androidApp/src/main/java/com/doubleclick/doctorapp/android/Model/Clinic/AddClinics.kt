package com.doubleclick.doctorapp.android.Model.Clinic

data class AddClinics(
    val name: String,
    val address: String,
    val reservation_time_start: String,
    val reservation_time_end: String,
    val opening_time: String,
    val closing_time: String,
    val overview: String,
    val governorate_id: String,
    val area_id: String,
    val doctor_id: String,
    val price: String,
)
