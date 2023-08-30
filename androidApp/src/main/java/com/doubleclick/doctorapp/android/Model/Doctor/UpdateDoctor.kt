package com.doubleclick.doctorapp.android.Model.Doctor

import android.os.Parcel
import android.os.Parcelable

data class UpdateDoctor(
    val facebook_page_link: String?,
    val facebook_page_name: String?,
    val general_specialty_id: Int,
    val instagram_page_link: String?,
    val instagram_page_name: String?,
    val name: String?,
    val status: String? = "Active",
    val specialization_id: Int,
    val website: String?
)