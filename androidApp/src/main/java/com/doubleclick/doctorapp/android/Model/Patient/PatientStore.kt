package com.doubleclick.doctorapp.android.Model.Patient

data class PatientStore(
    val name: String,
    val status: String = "Active",
    val phone: String,
    val telephone: String = "",
    val notes: String = "",
    val governorate_id: String,
    val area_id: String
)