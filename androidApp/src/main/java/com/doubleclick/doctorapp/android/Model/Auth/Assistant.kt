package com.doubleclick.doctorapp.android.Model.Auth

import android.os.Parcel
import android.os.Parcelable

data class Assistant(
    val clinic_id: Int,
    val doctor_id: Int,
    val id: Int,
    val user_id: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(clinic_id)
        parcel.writeInt(doctor_id)
        parcel.writeInt(id)
        parcel.writeInt(user_id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Assistant> {
        override fun createFromParcel(parcel: Parcel): Assistant {
            return Assistant(parcel)
        }

        override fun newArray(size: Int): Array<Assistant?> {
            return arrayOfNulls(size)
        }
    }
}