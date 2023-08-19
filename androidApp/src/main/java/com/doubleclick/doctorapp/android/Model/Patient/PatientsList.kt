package com.doubleclick.doctorapp.android.Model.Patient

import android.os.Parcel
import android.os.Parcelable

data class PatientsList(
    val `data`: List<PatientModel>?
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.createTypedArrayList(PatientModel)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(data)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PatientsList> {
        override fun createFromParcel(parcel: Parcel): PatientsList {
            return PatientsList(parcel)
        }

        override fun newArray(size: Int): Array<PatientsList?> {
            return arrayOfNulls(size)
        }
    }
}