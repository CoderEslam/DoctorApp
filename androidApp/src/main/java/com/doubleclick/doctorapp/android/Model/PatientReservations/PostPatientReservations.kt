package com.doubleclick.doctorapp.android.Model.PatientReservations

data class PostPatientReservations(
    val clinic_id: String,
    val doctor_id: String,
    val patient_id: String,
    val reservation_date: String,
    val type: String,
    val kind: String,
    val age: String,
    val patient_phone: String
)
