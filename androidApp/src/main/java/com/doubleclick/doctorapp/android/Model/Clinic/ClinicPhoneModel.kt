package com.doubleclick.doctorapp.android.Model.Clinic

import com.doubleclick.doctorapp.android.Model.User.UserModel

data class ClinicPhoneModel(
    val clinic: ClinicData,
    val clinic_id: Int,
    val id: Int,
    val phone: String,
    val type: String,
    val user: UserModel,
    val user_id: Int
)