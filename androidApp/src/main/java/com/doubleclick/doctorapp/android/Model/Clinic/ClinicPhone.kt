package com.doubleclick.doctorapp.android.Model.Clinic

import android.os.Parcel
import android.os.Parcelable

data class ClinicPhone(
    val clinic_id: Int,
    val id: Int,
    val phone: String?,
    val type: String?,
    val user_id: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(clinic_id)
        parcel.writeInt(id)
        parcel.writeString(phone)
        parcel.writeString(type)
        parcel.writeInt(user_id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ClinicPhone> {
        override fun createFromParcel(parcel: Parcel): ClinicPhone {
            return ClinicPhone(parcel)
        }

        override fun newArray(size: Int): Array<ClinicPhone?> {
            return arrayOfNulls(size)
        }
    }
}