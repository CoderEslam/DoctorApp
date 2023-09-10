package com.doubleclick.doctorapp.android.Model.GeneralSpecialization

import android.os.Parcel
import android.os.Parcelable

data class GeneralSpecializationModel(
    val id: Int,
    val name: String?,
    val status: String?,
    val user: User?,
    val user_id: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(User::class.java.classLoader),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(status)
        parcel.writeParcelable(user, flags)
        parcel.writeInt(user_id)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GeneralSpecializationModel

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }

    companion object CREATOR : Parcelable.Creator<GeneralSpecializationModel> {
        override fun createFromParcel(parcel: Parcel): GeneralSpecializationModel {
            return GeneralSpecializationModel(parcel)
        }

        override fun newArray(size: Int): Array<GeneralSpecializationModel?> {
            return arrayOfNulls(size)
        }
    }
}