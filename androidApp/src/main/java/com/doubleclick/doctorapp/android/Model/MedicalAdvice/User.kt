package com.doubleclick.doctorapp.android.Model.MedicalAdvice

import android.os.Parcel
import android.os.Parcelable

data class User(
    val device_token: String?,
    val email: String?,
    val email_verified_at: String?,
    val id: Int,
    val name: String?,
    val phone: String?,
    val status: String?,
    val user_image: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(device_token)
        parcel.writeString(email)
        parcel.writeString(email_verified_at)
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(phone)
        parcel.writeString(status)
        parcel.writeString(user_image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}