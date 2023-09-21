package com.doubleclick.doctorapp.android.Model.PatientReservations.PatientOldReservation

import android.os.Parcel
import android.os.Parcelable

data class Clinic(
    val address: String?,
    val area_id: Int,
    val closing_time: String?,
    val doctor_id: Int,
    val governorate_id: Int,
    val id: Int,
    val name: String?,
    val notes: String?,
    val opening_time: String?,
    val overview: String?,
    val price: String?,
    val reservation_time_end: String?,
    val reservation_time_start: String?,
    val status: String?,
    val user_id: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Clinic

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(address)
        parcel.writeInt(area_id)
        parcel.writeString(closing_time)
        parcel.writeInt(doctor_id)
        parcel.writeInt(governorate_id)
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(notes)
        parcel.writeString(opening_time)
        parcel.writeString(overview)
        parcel.writeString(price)
        parcel.writeString(reservation_time_end)
        parcel.writeString(reservation_time_start)
        parcel.writeString(status)
        parcel.writeInt(user_id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Clinic> {
        override fun createFromParcel(parcel: Parcel): Clinic {
            return Clinic(parcel)
        }

        override fun newArray(size: Int): Array<Clinic?> {
            return arrayOfNulls(size)
        }
    }
}