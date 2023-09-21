package com.doubleclick.doctorapp.android.Model.PatientReservations.ShowPatientOfDoctor

import android.os.Parcel
import android.os.Parcelable

data class ShowPatientOfDoctor(
    val `data`: List<ShowPatientOfDoctorModel>?
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.createTypedArrayList(ShowPatientOfDoctorModel)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(data)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ShowPatientOfDoctor> {
        override fun createFromParcel(parcel: Parcel): ShowPatientOfDoctor {
            return ShowPatientOfDoctor(parcel)
        }

        override fun newArray(size: Int): Array<ShowPatientOfDoctor?> {
            return arrayOfNulls(size)
        }
    }
}