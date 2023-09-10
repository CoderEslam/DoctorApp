package com.doubleclick.doctorapp.android.Model.Doctor

import android.os.Parcel
import android.os.Parcelable

data class DoctorModel(
    val clinics: List<Clinic>?,
    val doctor_phones: List<DoctorPhone>?,
    val facebook_page_link: String?,
    val facebook_page_name: String?,
    val general_specialty: GeneralSpecialty?,
    val general_specialty_id: Int,
    val id: Int,
    val instagram_page_link: String?,
    val instagram_page_name: String?,
    val doctor_image: String?,
    val name: String?,
    val specialization: Specialization?,
    val specialization_id: Int,
    val status: String?,
    val user: User?,
    val user_id: Int,
    val website: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.createTypedArrayList(Clinic),
        parcel.createTypedArrayList(DoctorPhone),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(GeneralSpecialty::class.java.classLoader),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Specialization::class.java.classLoader),
        parcel.readInt(),
        parcel.readString(),
        parcel.readParcelable(User::class.java.classLoader),
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(clinics)
        parcel.writeTypedList(doctor_phones)
        parcel.writeString(facebook_page_link)
        parcel.writeString(facebook_page_name)
        parcel.writeParcelable(general_specialty, flags)
        parcel.writeInt(general_specialty_id)
        parcel.writeInt(id)
        parcel.writeString(instagram_page_link)
        parcel.writeString(instagram_page_name)
        parcel.writeString(doctor_image)
        parcel.writeString(name)
        parcel.writeParcelable(specialization, flags)
        parcel.writeInt(specialization_id)
        parcel.writeString(status)
        parcel.writeParcelable(user, flags)
        parcel.writeInt(user_id)
        parcel.writeString(website)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DoctorModel

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }

    companion object CREATOR : Parcelable.Creator<DoctorModel> {
        override fun createFromParcel(parcel: Parcel): DoctorModel {
            return DoctorModel(parcel)
        }

        override fun newArray(size: Int): Array<DoctorModel?> {
            return arrayOfNulls(size)
        }
    }
}