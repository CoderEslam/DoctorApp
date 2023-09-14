package com.doubleclick.doctorapp.android.Model.Specialization

import android.os.Parcel
import android.os.Parcelable

data class SpecializationModel(
    val id: Int,
    val name: String?,
    val specialization_image: String?,
    val status: String?,
    val user_id: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(specialization_image)
        parcel.writeString(status)
        parcel.writeInt(user_id)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SpecializationModel

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }

    companion object CREATOR : Parcelable.Creator<SpecializationModel> {
        override fun createFromParcel(parcel: Parcel): SpecializationModel {
            return SpecializationModel(parcel)
        }

        override fun newArray(size: Int): Array<SpecializationModel?> {
            return arrayOfNulls(size)
        }
    }
}