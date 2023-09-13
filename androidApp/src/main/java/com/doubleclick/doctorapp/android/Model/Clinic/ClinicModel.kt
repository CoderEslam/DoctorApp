package com.doubleclick.doctorapp.android.Model.Clinic

import android.os.Parcel
import android.os.Parcelable

data class ClinicModel(
    val address: String?,
    val area: Area?,
    val area_id: Int,
    val clinic_phones: List<ClinicPhone>?,
    val closing_time: String?,
    val doctor: Doctor?,
    val doctor_id: Int,
    val governorate: Governorate?,
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
    val user: User?,
    val user_id: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readParcelable(Area::class.java.classLoader),
        parcel.readInt(),
        parcel.createTypedArrayList(ClinicPhone),
        parcel.readString(),
        parcel.readParcelable(Doctor::class.java.classLoader),
        parcel.readInt(),
        parcel.readParcelable(Governorate::class.java.classLoader),
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
        parcel.readParcelable(User::class.java.classLoader),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(address)
        parcel.writeParcelable(area, flags)
        parcel.writeInt(area_id)
        parcel.writeTypedList(clinic_phones)
        parcel.writeString(closing_time)
        parcel.writeParcelable(doctor, flags)
        parcel.writeInt(doctor_id)
        parcel.writeParcelable(governorate, flags)
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
        parcel.writeParcelable(user, flags)
        parcel.writeInt(user_id)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ClinicModel

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }

    companion object CREATOR : Parcelable.Creator<ClinicModel> {
        override fun createFromParcel(parcel: Parcel): ClinicModel {
            return ClinicModel(parcel)
        }

        override fun newArray(size: Int): Array<ClinicModel?> {
            return arrayOfNulls(size)
        }
    }
}