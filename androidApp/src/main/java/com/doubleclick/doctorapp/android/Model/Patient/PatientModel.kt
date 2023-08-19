package com.doubleclick.doctorapp.android.Model.Patient

import android.os.Parcel
import android.os.Parcelable
import com.doubleclick.doctorapp.android.Model.Area.Area

data class PatientModel(
    val alcohol_drinking: Int,
//    val area: String?,
    val area_id: Int,
    val blood_type: String?,
    val governorate: Governorate?,
    val governorate_id: Int,
    val height: Int,
    val id: Int,
    val materiel_status: Int,
    val name: String?,
    val notes: String?,
    val phone: String?,
    val smoking: Int?,
    val status: String?,
    val telephone: String?,
    val user: User?,
    val user_id: Int,
    val weight: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
//        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readParcelable(Governorate::class.java.classLoader),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(User::class.java.classLoader),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(alcohol_drinking)
//        parcel.writeString(area)
        parcel.writeInt(area_id)
        parcel.writeString(blood_type)
        parcel.writeParcelable(governorate, flags)
        parcel.writeInt(governorate_id)
        parcel.writeInt(height)
        parcel.writeInt(id)
        parcel.writeInt(materiel_status)
        parcel.writeString(name)
        parcel.writeString(notes)
        parcel.writeString(phone)
        parcel.writeValue(smoking)
        parcel.writeString(status)
        parcel.writeString(telephone)
        parcel.writeParcelable(user, flags)
        parcel.writeInt(user_id)
        parcel.writeInt(weight)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PatientModel> {
        override fun createFromParcel(parcel: Parcel): PatientModel {
            return PatientModel(parcel)
        }

        override fun newArray(size: Int): Array<PatientModel?> {
            return arrayOfNulls(size)
        }
    }
}