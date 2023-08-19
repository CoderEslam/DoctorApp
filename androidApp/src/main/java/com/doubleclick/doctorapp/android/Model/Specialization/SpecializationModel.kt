package com.doubleclick.doctorapp.android.Model.Specialization

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import com.doubleclick.doctorapp.android.Model.User.UserModel

data class SpecializationModel(
    val id: Int,
    val name: String?,
    val status: String?,
    val user: UserModel?,
    val user_id: Int
) : Parcelable {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(UserModel::class.java.classLoader, UserModel::class.java),
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

    companion object CREATOR : Parcelable.Creator<SpecializationModel> {
        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        override fun createFromParcel(parcel: Parcel): SpecializationModel {
            return SpecializationModel(parcel)
        }

        override fun newArray(size: Int): Array<SpecializationModel?> {
            return arrayOfNulls(size)
        }
    }
}