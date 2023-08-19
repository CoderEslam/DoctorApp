package com.doubleclick.doctorapp.android.Model.PatientReservations

import android.os.Parcel
import android.os.Parcelable

data class Patient(
    val area_id: Int,
    val governorate_id: Int,
    val id: Int,
    val name: String?,
    val notes: String?,
    val phone: String?,
    val status: String?,
    val telephone: String?,
    val user_id: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(area_id)
        parcel.writeInt(governorate_id)
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(notes)
        parcel.writeString(phone)
        parcel.writeString(status)
        parcel.writeString(telephone)
        parcel.writeInt(user_id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Patient> {
        override fun createFromParcel(parcel: Parcel): Patient {
            return Patient(parcel)
        }

        override fun newArray(size: Int): Array<Patient?> {
            return arrayOfNulls(size)
        }
    }
}