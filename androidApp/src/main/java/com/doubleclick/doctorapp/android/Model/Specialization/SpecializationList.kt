package com.doubleclick.doctorapp.android.Model.Specialization

import android.os.Parcel
import android.os.Parcelable

data class SpecializationList(
    val `data`: List<SpecializationModel>?
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.createTypedArrayList(SpecializationModel)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(data)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SpecializationList> {
        override fun createFromParcel(parcel: Parcel): SpecializationList {
            return SpecializationList(parcel)
        }

        override fun newArray(size: Int): Array<SpecializationList?> {
            return arrayOfNulls(size)
        }
    }
}