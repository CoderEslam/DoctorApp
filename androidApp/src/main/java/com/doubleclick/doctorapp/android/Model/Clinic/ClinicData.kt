package com.doubleclick.doctorapp.android.Model.Clinic

import com.doubleclick.doctorapp.android.Model.Area.Area
import com.doubleclick.doctorapp.android.Model.Doctor.DoctorModel
import com.doubleclick.doctorapp.android.Model.Governorates.Governorate
import com.doubleclick.doctorapp.android.Model.User.UserModel

data class ClinicData(
    val address: String,
    val area: Area,
    val area_id: Int,
    val clinic_phones: List<ClinicPhone>,
    val closing_time: String,
    val doctor: DoctorModel,
    val doctor_id: Int,
    val governorate: Governorate,
    val governorate_id: Int,
    val id: Int,
    val name: String,
    val notes: String,
    val opening_time: String,
    val overview: String,
    val reservation_time_end: String,
    val reservation_time_start: String,
    val status: String,
    val user: UserModel,
    val user_id: Int
):java.io.Serializable