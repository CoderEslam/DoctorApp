package com.doubleclick.doctorapp.android.Model.MedicalAdvice

import android.os.Parcel
import android.os.Parcelable

data class MedicalAdvice(
    val `data`: List<MedicalAdviceModel>?
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.createTypedArrayList(MedicalAdviceModel)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(data)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MedicalAdvice> {
        override fun createFromParcel(parcel: Parcel): MedicalAdvice {
            return MedicalAdvice(parcel)
        }

        override fun newArray(size: Int): Array<MedicalAdvice?> {
            return arrayOfNulls(size)
        }
    }
}