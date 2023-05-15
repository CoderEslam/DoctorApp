package com.doubleclick.doctorapp.android.Model.PatientReservations

import com.doubleclick.doctorapp.android.Model.User.UserModel

data class Data(
    val attend: String,
    val cancel_reason: Any,
    val clinic: Any,
    val clinic_id: Int,
    val doctor: Any,
    val doctor_id: Int,
    val id: Int,
    val notes: Any,
    val patient: Patient,
    val patient_id: Int,
    val reservation_date: String,
    val status: String,
    val type: String,
    val user: UserModel,
    val user_id: Int
)