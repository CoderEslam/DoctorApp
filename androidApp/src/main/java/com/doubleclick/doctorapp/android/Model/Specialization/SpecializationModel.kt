package com.doubleclick.doctorapp.android.Model.Specialization

import com.doubleclick.doctorapp.android.Model.User.UserModel

data class SpecializationModel(
    val id: Int,
    val name: String,
    val status: String,
    val user: UserModel,
    val user_id: Int
):java.io.Serializable