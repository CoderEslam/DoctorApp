package com.doubleclick.doctorapp.android.Model.Patient

data class PatientVisits(
    val type: String,
    val doctor_id: String,
    val patient_id: String,
    val clinic_id: String,
    val reservation_date: String,
    val patient_reservation_id: String,
    val diagnosis: String,
)
