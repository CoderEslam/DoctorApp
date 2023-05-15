package com.doubleclick.doctorapp.android.Model.Doctor

import com.doubleclick.doctorapp.android.Model.Clinic.ClinicData
import com.doubleclick.doctorapp.android.Model.Clinic.ClinicList
import com.doubleclick.doctorapp.android.Model.Clinic.ClinicPhone
import com.doubleclick.doctorapp.android.Model.Specialization.GeneralSpecialty
import com.doubleclick.doctorapp.android.Model.Specialization.SpecializationModel
import com.doubleclick.doctorapp.android.Model.User.UserModel

data class DoctorData(
    val clinics: List<ClinicData>,
    val created_at: String,
    val doctor_phones: List<ClinicPhone>,
    val facebook_page_link: String,
    val facebook_page_name: String,
    val general_specialty: GeneralSpecialty,
    val general_specialty_id: Int,
    val id: Int,
    val instagram_page_link: String,
    val instagram_page_name: String,
    val name: String,
    val specialization: SpecializationModel,
    val specialization_id: Int,
    val status: String,
    val updated_at: String,
    val user: UserModel,
    val user_id: Int,
    val website: String
):java.io.Serializable