package com.doubleclick.doctorapp.android.Model.Doctor

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import com.doubleclick.doctorapp.android.Model.Clinic.ClinicData
import com.doubleclick.doctorapp.android.Model.Clinic.ClinicList
import com.doubleclick.doctorapp.android.Model.Clinic.ClinicPhone
import com.doubleclick.doctorapp.android.Model.Specialization.GeneralSpecialty
import com.doubleclick.doctorapp.android.Model.Specialization.SpecializationModel
import com.doubleclick.doctorapp.android.Model.User.UserModel

data class DoctorData(
    val clinics: List<ClinicData>?,
    val doctor_phones: List<ClinicPhone>?,
    val facebook_page_link: String?,
    val facebook_page_name: String?,
    val general_specialty: GeneralSpecialty?,
    val general_specialty_id: Int,
    val id: Int,
    val instagram_page_link: String?,
    val instagram_page_name: String?,
    val name: String?,
    val specialization: SpecializationModel?,
    val specialization_id: Int,
    val status: String?,
    val user: UserModel?,
    val user_id: Int,
    val website: String?
) : Parcelable {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    constructor(parcel: Parcel) : this(
        parcel.createTypedArrayList(ClinicData),
        parcel.createTypedArrayList(ClinicPhone),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(
            GeneralSpecialty::class.java.classLoader,
            GeneralSpecialty::class.java
        ),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(
            SpecializationModel::class.java.classLoader,
            SpecializationModel::class.java
        ),
        parcel.readInt(),
        parcel.readString(),
        parcel.readParcelable(UserModel::class.java.classLoader, UserModel::class.java),
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

    companion object CREATOR : Parcelable.Creator<DoctorData> {
        override fun createFromParcel(parcel: Parcel): DoctorData {
            return DoctorData(parcel)
        }

        override fun newArray(size: Int): Array<DoctorData?> {
            return arrayOfNulls(size)
        }
    }
}