package com.doubleclick.doctorapp.android.Model.Days

import android.os.Parcel
import android.os.Parcelable

data class DaysAtClinicModel(
    val clinic_id: Int,
    val day_id: Int,
    val doctor_id: Int,
    val from_time: String?,
    val id: Int,
    val to_time: String?,
    val user_id: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(clinic_id)
        parcel.writeInt(day_id)
        parcel.writeInt(doctor_id)
        parcel.writeString(from_time)
        parcel.writeInt(id)
        parcel.writeString(to_time)
        parcel.writeInt(user_id)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DaysAtClinicModel

        if (day_id != other.day_id) return false

        return true
    }

    override fun hashCode(): Int {
        return day_id
    }

    companion object CREATOR : Parcelable.Creator<DaysAtClinicModel> {
        override fun createFromParcel(parcel: Parcel): DaysAtClinicModel {
            return DaysAtClinicModel(parcel)
        }

        override fun newArray(size: Int): Array<DaysAtClinicModel?> {
            return arrayOfNulls(size)
        }
    }
}