package com.doubleclick.doctorapp.android.Model.Area

import android.os.Parcel
import android.os.Parcelable

data class Araes(
    val `data`: List<AreaModel>?
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.createTypedArrayList(AreaModel)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(data)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Araes> {
        override fun createFromParcel(parcel: Parcel): Araes {
            return Araes(parcel)
        }

        override fun newArray(size: Int): Array<Araes?> {
            return arrayOfNulls(size)
        }
    }
}