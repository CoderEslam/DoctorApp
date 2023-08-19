package com.doubleclick.doctorapp.android.Model.Specialization

import android.os.Parcel
import android.os.Parcelable

data class GeneralSpecialty(
    val id: Int,
    val name: String?,
    val status: String?,
    val user_id: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(status)
        parcel.writeInt(user_id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GeneralSpecialty> {
        override fun createFromParcel(parcel: Parcel): GeneralSpecialty {
            return GeneralSpecialty(parcel)
        }

        override fun newArray(size: Int): Array<GeneralSpecialty?> {
            return arrayOfNulls(size)
        }
    }
}