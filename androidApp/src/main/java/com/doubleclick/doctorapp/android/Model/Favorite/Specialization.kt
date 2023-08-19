package com.doubleclick.doctorapp.android.Model.Favorite

import android.os.Parcel
import android.os.Parcelable

data class Specialization(
    val id: Int,
    val name: String?,
    val status: String?,
    val user_id: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(status)
        parcel.writeInt(user_id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Specialization> {
        override fun createFromParcel(parcel: Parcel): Specialization {
            return Specialization(parcel)
        }

        override fun newArray(size: Int): Array<Specialization?> {
            return arrayOfNulls(size)
        }
    }
}