package com.doubleclick.doctorapp.android.Model.PatientReservations.PatientOldReservation

import android.os.Parcel
import android.os.Parcelable

data class Patient(
    val alcohol_drinking: Int,
    val area_id: Int,
    val blood_type: String?,
    val governorate_id: Int,
    val height: Int,
    val id: Int,
    val materiel_status: Int,
    val name: String?,
    val notes: String?,
    val phone: String?,
    val smoking: Int,
    val status: String?,
    val telephone: String?,
    val user_id: Int,
    val weight: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(alcohol_drinking)
        parcel.writeInt(area_id)
        parcel.writeString(blood_type)
        parcel.writeInt(governorate_id)
        parcel.writeInt(height)
        parcel.writeInt(id)
        parcel.writeInt(materiel_status)
        parcel.writeString(name)
        parcel.writeString(notes)
        parcel.writeString(phone)
        parcel.writeInt(smoking)
        parcel.writeString(status)
        parcel.writeString(telephone)
        parcel.writeInt(user_id)
        parcel.writeInt(weight)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Patient

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
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