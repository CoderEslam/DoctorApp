package com.doubleclick.doctorapp.android.Model.MedicalAdvice

import android.os.Parcel
import android.os.Parcelable

data class MedicalAdviceModel(
    val doctor: Doctor?,
    val doctor_id: Int,
    val id: Int,
    val user_id: Int,
    val video_name: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(Doctor::class.java.classLoader),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(doctor, flags)
        parcel.writeInt(doctor_id)
        parcel.writeInt(id)
        parcel.writeInt(user_id)
        parcel.writeString(video_name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MedicalAdviceModel> {
        override fun createFromParcel(parcel: Parcel): MedicalAdviceModel {
            return MedicalAdviceModel(parcel)
        }

        override fun newArray(size: Int): Array<MedicalAdviceModel?> {
            return arrayOfNulls(size)
        }
    }
}