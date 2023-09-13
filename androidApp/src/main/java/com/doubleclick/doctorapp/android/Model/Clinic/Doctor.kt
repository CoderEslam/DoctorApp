package com.doubleclick.doctorapp.android.Model.Clinic

import android.os.Parcel
import android.os.Parcelable

data class Doctor(
    val doctor_image: String?,
    val facebook_page_link: String?,
    val facebook_page_name: String?,
    val general_specialty_id: Int,
    val id: Int,
    val instagram_page_link: String?,
    val instagram_page_name: String?,
    val name: String?,
    val specialization_id: Int,
    val status: String?,
    val user_id: Int,
    val website: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Doctor

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(doctor_image)
        parcel.writeString(facebook_page_link)
        parcel.writeString(facebook_page_name)
        parcel.writeInt(general_specialty_id)
        parcel.writeInt(id)
        parcel.writeString(instagram_page_link)
        parcel.writeString(instagram_page_name)
        parcel.writeString(name)
        parcel.writeInt(specialization_id)
        parcel.writeString(status)
        parcel.writeInt(user_id)
        parcel.writeString(website)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Doctor> {
        override fun createFromParcel(parcel: Parcel): Doctor {
            return Doctor(parcel)
        }

        override fun newArray(size: Int): Array<Doctor?> {
            return arrayOfNulls(size)
        }
    }
}