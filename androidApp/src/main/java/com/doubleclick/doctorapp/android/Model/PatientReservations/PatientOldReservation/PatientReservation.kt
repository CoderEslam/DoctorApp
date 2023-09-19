package com.doubleclick.doctorapp.android.Model.PatientReservations.PatientOldReservation

data class PatientReservation(
    val age: String,
    val attend: String,
    val cancel_reason: String,
    val clinic_id: Int,
    val doctor_id: Int,
    val id: Int,
    val kind: String,
    val notes: String,
    val patient_id: Int,
    val patient_phone: String,
    val reservation_date: String,
    val status: String,
    val type: String,
    val user_id: Int
)