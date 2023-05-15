package com.doubleclick.doctorapp.android.Model.Auth

data class Login(
    val email: String = "",
    val phone: String = "",
    val password: String,
){
    override fun toString(): String {
        return "Login(email='$email', phone='$phone', password='$password')"
    }
}
