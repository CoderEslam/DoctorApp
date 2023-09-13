package com.doubleclick.doctorapp.android.Model.Clinic

import android.os.Parcel
import android.os.Parcelable

data class ClinicList(
    val `data`: List<ClinicModel>?
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.createTypedArrayList(ClinicModel)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(data)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ClinicList> {
        override fun createFromParcel(parcel: Parcel): ClinicList {
            return ClinicList(parcel)
        }

        override fun newArray(size: Int): Array<ClinicList?> {
            return arrayOfNulls(size)
        }
    }
}