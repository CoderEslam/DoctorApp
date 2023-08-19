package com.doubleclick.doctorapp.android.Model.Favorite

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi

data class FavoriteModel(
    val doctor: Doctor?,
    val doctor_id: Int,
    val general_specialty: GeneralSpecialty?,
    val id: Int,
    val specialization: Specialization?,
    val user: User?,
    val user_id: Int
) : Parcelable {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(Doctor::class.java.classLoader,Doctor::class.java),
        parcel.readInt(),
        parcel.readParcelable(GeneralSpecialty::class.java.classLoader,GeneralSpecialty::class.java),
        parcel.readInt(),
        parcel.readParcelable(Specialization::class.java.classLoader,Specialization::class.java),
        parcel.readParcelable(User::class.java.classLoader,User::class.java),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(doctor, flags)
        parcel.writeInt(doctor_id)
        parcel.writeParcelable(general_specialty, flags)
        parcel.writeInt(id)
        parcel.writeParcelable(specialization, flags)
        parcel.writeParcelable(user, flags)
        parcel.writeInt(user_id)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FavoriteModel

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }

    companion object CREATOR : Parcelable.Creator<FavoriteModel> {
        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        override fun createFromParcel(parcel: Parcel): FavoriteModel {
            return FavoriteModel(parcel)
        }

        override fun newArray(size: Int): Array<FavoriteModel?> {
            return arrayOfNulls(size)
        }
    }
}