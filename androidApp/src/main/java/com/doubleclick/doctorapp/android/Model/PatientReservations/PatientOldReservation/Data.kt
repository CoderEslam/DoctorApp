package com.doubleclick.doctorapp.android.Model.PatientReservations.PatientOldReservation

data class Data(
    val clinic: Clinic,
    val clinic_id: Int,
    val diagnosis: String,
    val doctor: Doctor,
    val doctor_id: Int,
    val id: Int,
    val images: String,
    val notes: String,
    val patient: Patient,
    val patient_id: Int,
    val patient_reservation: PatientReservation,
    val patient_reservation_id: Int,
    val records: String,
    val status: String,
    val user: User,
    val user_id: Int
)