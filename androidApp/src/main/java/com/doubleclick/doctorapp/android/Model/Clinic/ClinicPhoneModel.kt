package com.doubleclick.doctorapp.android.Model.Clinic

import android.os.Parcel
import android.os.Parcelable
import com.doubleclick.doctorapp.android.Model.User.UserModel

data class ClinicPhoneModel(
    val clinic: ClinicModel?,
    val clinic_id: Int,
    val id: Int,
    val phone: String?,
    val type: String?,
    val user: UserModel?,
    val user_id: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(ClinicModel::class.java.classLoader),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(UserModel::class.java.classLoader),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(clinic, flags)
        parcel.writeInt(clinic_id)
        parcel.writeInt(id)
        parcel.writeString(phone)
        parcel.writeString(type)
        parcel.writeParcelable(user, flags)
        parcel.writeInt(user_id)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ClinicPhoneModel

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }

    companion object CREATOR : Parcelable.Creator<ClinicPhoneModel> {
        override fun createFromParcel(parcel: Parcel): ClinicPhoneModel {
            return ClinicPhoneModel(parcel)
        }

        override fun newArray(size: Int): Array<ClinicPhoneModel?> {
            return arrayOfNulls(size)
        }
    }
}