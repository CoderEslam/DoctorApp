package com.doubleclick.doctorapp.android.Model.Specialization

import android.os.Parcel
import android.os.Parcelable
import com.doubleclick.doctorapp.android.Model.User.UserModel

data class SpecializationModel(
    val id: Int,
    val name: String?,
    val specialization_image: String?,
    val status: String?,
    val user: UserModel?,
    val user_id: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(UserModel::class.java.classLoader),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(specialization_image)
        parcel.writeString(status)
        parcel.writeParcelable(user, flags)
        parcel.writeInt(user_id)
    }

    override fun describeContents(): Int {
        return 0
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