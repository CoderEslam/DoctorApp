package com.doubleclick.doctorapp.android.Model.Assistants

import android.os.Parcel
import android.os.Parcelable

data class AssistantsModel(
    val doctor: Doctor?,
    val doctor_id: Int,
    val id: Int,
    val name: String?,
    val phone: String?,
    val status: String?,
    val user: User?,
    val user_id: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(Doctor::class.java.classLoader),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(User::class.java.classLoader),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(doctor, flags)
        parcel.writeInt(doctor_id)
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(phone)
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

        other as AssistantsModel

        if (doctor_id != other.doctor_id) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = doctor_id
        result = 31 * result + id
        return result
    }

    companion object CREATOR : Parcelable.Creator<AssistantsModel> {
        override fun createFromParcel(parcel: Parcel): AssistantsModel {
            return AssistantsModel(parcel)
        }

        override fun newArray(size: Int): Array<AssistantsModel?> {
            return arrayOfNulls(size)
        }
    }
}