package com.doubleclick.doctorapp.android.Model.Clinic

import android.os.Parcel
import android.os.Parcelable

data class ClinicPhoneList(
    val `data`: List<ClinicPhoneModel>?
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.createTypedArrayList(ClinicPhoneModel)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(data)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ClinicPhoneList> {
        override fun createFromParcel(parcel: Parcel): ClinicPhoneList {
            return ClinicPhoneList(parcel)
        }

        override fun newArray(size: Int): Array<ClinicPhoneList?> {
            return arrayOfNulls(size)
        }
    }
}