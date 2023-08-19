package com.doubleclick.doctorapp.android.Model.User

import android.os.Parcel
import android.os.Parcelable

data class UserModel(
    val device_token: String?,
    val email: String?,
    val id: Int,
    val name: String?,
    val phone: String?,
    val roles: List<Role>?,
    val status: String?,
    val user_image: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.createTypedArrayList(Role),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(device_token)
        parcel.writeString(email)
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(phone)
        parcel.writeTypedList(roles)
        parcel.writeString(status)
        parcel.writeString(user_image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserModel> {
        override fun createFromParcel(parcel: Parcel): UserModel {
            return UserModel(parcel)
        }

        override fun newArray(size: Int): Array<UserModel?> {
            return arrayOfNulls(size)
        }
    }
}