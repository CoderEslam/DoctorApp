package com.doubleclick.doctorapp.android.Model.Doctor

import android.os.Parcel
import android.os.Parcelable

data class DoctorsList(
    val `data`: List<DoctorModel>?
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.createTypedArrayList(DoctorModel)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(data)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DoctorsList> {
        override fun createFromParcel(parcel: Parcel): DoctorsList {
            return DoctorsList(parcel)
        }

        override fun newArray(size: Int): Array<DoctorsList?> {
            return arrayOfNulls(size)
        }
    }
}