package com.doubleclick.doctorapp.android.Model.PatientReservations

import android.os.Parcel
import android.os.Parcelable

data class PatientReservationsModel(
    val age: String?,
    val attend: String?,
    val cancel_reason: String?,
    val clinic: Clinic?,
    val clinic_id: Int,
    val doctor: Doctor?,
    val doctor_id: Int,
    val id: Int,
    val kind: String?,
    val notes: String?,
    val patient: Patient?,
    val patient_id: Int,
    val patient_phone: String?,
    val reservation_date: String?,
    val status: String?,
    val type: String?,
    val user: User?,
    val user_id: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Clinic::class.java.classLoader),
        parcel.readInt(),
        parcel.readParcelable(Doctor::class.java.classLoader),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Patient::class.java.classLoader),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        TODO("user"),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(age)
        parcel.writeString(attend)
        parcel.writeString(cancel_reason)
        parcel.writeParcelable(clinic, flags)
        parcel.writeInt(clinic_id)
        parcel.writeParcelable(doctor, flags)
        parcel.writeInt(doctor_id)
        parcel.writeInt(id)
        parcel.writeString(kind)
        parcel.writeString(notes)
        parcel.writeParcelable(patient, flags)
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

        other as PatientReservationsModel

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }

    companion object CREATOR : Parcelable.Creator<PatientReservationsModel> {
        override fun createFromParcel(parcel: Parcel): PatientReservationsModel {
            return PatientReservationsModel(parcel)
        }

        override fun newArray(size: Int): Array<PatientReservationsModel?> {
            return arrayOfNulls(size)
        }
    }
}