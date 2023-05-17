package com.doubleclick.doctorapp.android.Model.PatientReservations

import com.doubleclick.doctorapp.android.Model.Doctor.Doctor
import com.doubleclick.doctorapp.android.Model.User.UserModel

data class PatientReservationsModel(
    val age: String,
    val attend: String,
    val cancel_reason: String,
    val clinic: Clinic?,
    val clinic_id: Int,
    val doctor: Doctor?,
    val doctor_id: Int,
    val id: Int,
    val kind: String,
    val notes: String,
    val patient: Patient?,
    val patient_id: Int,
    val patient_phone: String,
    val reservation_date: String,
    val status: String,
    val type: String,
    val user: UserModel?,
    val user_id: Int
)