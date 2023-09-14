package com.doubleclick.doctorapp.android.Model.Patient

import android.os.Parcel
import android.os.Parcelable

data class Visit(
    val clinic_id: Int,
    val diagnosis: String?,
    val doctor_id: Int,
    val id: Int,
    val images: String?,
    val notes: String?,
    val patient_id: Int,
    val patient_reservation_id: Int,
    val records: String?,
    val status: String?,
    val user_id: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(clinic_id)
        parcel.writeString(diagnosis)
        parcel.writeInt(doctor_id)
        parcel.writeInt(id)
        parcel.writeString(images)
        parcel.writeString(notes)
        parcel.writeInt(patient_id)
        parcel.writeInt(patient_reservation_id)
        parcel.writeString(records)
        parcel.writeString(status)
        parcel.writeInt(user_id)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Visit

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }

    companion object CREATOR : Parcelable.Creator<Visit> {
        override fun createFromParcel(parcel: Parcel): Visit {
            return Visit(parcel)
        }

        override fun newArray(size: Int): Array<Visit?> {
            return arrayOfNulls(size)
        }
    }
}