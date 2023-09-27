package com.doubleclick.doctorapp.android.Model.Patient.MyVisits

import android.os.Parcel
import android.os.Parcelable

data class PatientReservation(
    val age: String?,
    val attend: String?,
    val cancel_reason: String?,
    val clinic_id: Int,
    val doctor_id: Int,
    val id: Int,
    val kind: String?,
    val notes: String?,
    val patient_id: Int,
    val patient_phone: String?,
    val reservation_date: String?,
    val status: String?,
    val type: String?,
    val user_id: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(age)
        parcel.writeString(attend)
        parcel.writeString(cancel_reason)
        parcel.writeInt(clinic_id)
        parcel.writeInt(doctor_id)
        parcel.writeInt(id)
        parcel.writeString(kind)
        parcel.writeString(notes)
        parcel.writeInt(patient_id)
        parcel.writeString(patient_phone)
        parcel.writeString(reservation_date)
        parcel.writeString(status)
        parcel.writeString(type)
        parcel.writeInt(user_id)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PatientReservation

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }

    companion object CREATOR : Parcelable.Creator<PatientReservation> {
        override fun createFromParcel(parcel: Parcel): PatientReservation {
            return PatientReservation(parcel)
        }

        override fun newArray(size: Int): Array<PatientReservation?> {
            return arrayOfNulls(size)
        }
    }
}