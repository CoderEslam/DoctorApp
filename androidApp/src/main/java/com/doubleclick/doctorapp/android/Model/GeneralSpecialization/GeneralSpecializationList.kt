package com.doubleclick.doctorapp.android.Model.GeneralSpecialization

import android.os.Parcel
import android.os.Parcelable

data class GeneralSpecializationList(
    val `data`: List<GeneralSpecializationModel>?
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.createTypedArrayList(GeneralSpecializationModel)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(data)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GeneralSpecializationList> {
        override fun createFromParcel(parcel: Parcel): GeneralSpecializationList {
            return GeneralSpecializationList(parcel)
        }

        override fun newArray(size: Int): Array<GeneralSpecializationList?> {
            return arrayOfNulls(size)
        }
    }
}