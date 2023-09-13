package com.doubleclick.doctorapp.android.Model.Patient

data class PatientStore(
    val name: String,
    val status: String = "Active",
    val phone: String,
    val telephone: String = "",
    val notes: String = "",
    val governorate_id: String,
    val area_id: String,
    val smoking: String,
    val weight: String,
    val alcohol_drinking: String,
    val height: String,
    val blood_type: String,
    val materiel_status: String
)