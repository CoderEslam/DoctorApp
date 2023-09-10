package com.doubleclick.doctorapp.android.Model.Auth

import android.os.Parcel
import android.os.Parcelable

data class LoginCallback(
    val role: String?,
    val user: User?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readParcelable(User::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(role)
        parcel.writeParcelable(user, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LoginCallback> {
        override fun createFromParcel(parcel: Parcel): LoginCallback {
            return LoginCallback(parcel)
        }

        override fun newArray(size: Int): Array<LoginCallback?> {
            return arrayOfNulls(size)
        }
    }
}