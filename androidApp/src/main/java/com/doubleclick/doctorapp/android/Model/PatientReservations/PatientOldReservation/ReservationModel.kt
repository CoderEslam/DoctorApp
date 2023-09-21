package com.doubleclick.doctorapp.android.Model.PatientReservations.PatientOldReservation

import android.os.Parcel
import android.os.Parcelable

data class ReservationModel(
    val clinic: Clinic?,
    val clinic_id: Int,
    val diagnosis: String?,
    val doctor: Doctor?,
    val doctor_id: Int,
    val id: Int,
    val images: String?,
    val notes: String?,
    val patient: Patient?,
    val patient_id: Int,
    val patient_reservation: PatientReservation?,
    val patient_reservation_id: Int,
    val records: String?,
    val status: String?,
    val user: User?,
    val user_id: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(Clinic::class.java.classLoader),
        parcel.readInt(),
        parcel.readString(),
        parcel.readParcelable(Doctor::class.java.classLoader),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Patient::class.java.classLoader),
        parcel.readInt(),
        parcel.readParcelable(PatientReservation::class.java.classLoader),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(User::class.java.classLoader),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(clinic, flags)
        parcel.writeInt(clinic_id)
        parcel.writeString(diagnosis)
        parcel.writeParcelable(doctor, flags)
        parcel.writeInt(doctor_id)
        parcel.writeInt(id)
        parcel.writeString(images)
        parcel.writeString(notes)
        parcel.writeParcelable(patient, flags)
        parcel.writeInt(patient_id)
        parcel.writeParcelable(patient_reservation, flags)
        parcel.writeInt(patient_reservation_id)
        parcel.writeString(records)
        parcel.writeString(status)
        parcel.writeParcelable(user, flags)
        parcel.writeInt(user_id)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ReservationModel

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }

    companion object CREATOR : Parcelable.Creator<ReservationModel> {
        override fun createFromParcel(parcel: Parcel): ReservationModel {
            return ReservationModel(parcel)
        }

        override fun newArray(size: Int): Array<ReservationModel?> {
            return arrayOfNulls(size)
        }
    }
}