package com.doubleclick.doctorapp.android.Model.Clinic

import android.os.Parcel
import android.os.Parcelable

data class AddClinics(
    val name: String?,
    val address: String?,
    val reservation_time_start: String?,
    val reservation_time_end: String?,
    val opening_time: String?,
    val closing_time: String?,
    val overview: String?,
    val governorate_id: String?,
    val area_id: String?,
    val doctor_id: String?,
    val price: String?,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(address)
        parcel.writeString(reservation_time_start)
        parcel.writeString(reservation_time_end)
        parcel.writeString(opening_time)
        parcel.writeString(closing_time)
        parcel.writeString(overview)
        parcel.writeString(governorate_id)
        parcel.writeString(area_id)
        parcel.writeString(doctor_id)
        parcel.writeString(price)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AddClinics> {
        override fun createFromParcel(parcel: Parcel): AddClinics {
            return AddClinics(parcel)
        }

        override fun newArray(size: Int): Array<AddClinics?> {
            return arrayOfNulls(size)
        }
    }
}
