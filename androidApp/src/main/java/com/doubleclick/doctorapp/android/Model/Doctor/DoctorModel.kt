package com.doubleclick.doctorapp.android.Model.Doctor

data class DoctorModel(
    val facebook_page_link: String,
    val facebook_page_name: String,
    val general_specialty_id: Int,
    val id: Int,
    val instagram_page_link: String,
    val instagram_page_name: String,
    val name: String,
    val specialization_id: Int,
    val status: String,
    val user_id: Int,
    val website: String
)