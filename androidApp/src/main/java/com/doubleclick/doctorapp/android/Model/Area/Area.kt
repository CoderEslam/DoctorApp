package com.doubleclick.doctorapp.android.Model.Area

import android.os.Parcel
import android.os.Parcelable

data class Area(
    val governorate_id: Int,
    val id: Int,
    val name: String?,
    val status: String?,
    val user_id: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(governorate_id)
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(status)
        parcel.writeInt(user_id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Area> {
        override fun createFromParcel(parcel: Parcel): Area {
            return Area(parcel)
        }

        override fun newArray(size: Int): Array<Area?> {
            return arrayOfNulls(size)
        }
    }
}