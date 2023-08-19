package com.doubleclick.doctorapp.android.Model.Clinic

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import com.doubleclick.doctorapp.android.Model.Area.Area
import com.doubleclick.doctorapp.android.Model.Doctor.DoctorModel
import com.doubleclick.doctorapp.android.Model.Governorates.Governorate
import com.doubleclick.doctorapp.android.Model.User.UserModel

data class ClinicData(
    val address: String?,
    val area: Area?,
    val area_id: Int,
    val clinic_phones: List<ClinicPhone>?,
    val closing_time: String?,
    val doctor: DoctorModel?,
    val doctor_id: Int,
    val governorate: Governorate?,
    val governorate_id: Int,
    val id: Int,
    val name: String?,
    val notes: String?,
    val opening_time: String?,
    val overview: String?,
    val reservation_time_end: String?,
    val reservation_time_start: String?,
    val status: String?,
    val user: UserModel?,
    val user_id: Int
) : Parcelable {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readParcelable(Area::class.java.classLoader, Area::class.java),
        parcel.readInt(),
        parcel.createTypedArrayList(ClinicPhone),
        parcel.readString(),
        parcel.readParcelable(DoctorModel::class.java.classLoader, DoctorModel::class.java),
        parcel.readInt(),
        parcel.readParcelable(Governorate::class.java.classLoader, Governorate::class.java),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(UserModel::class.java.classLoader, UserModel::class.java),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(address)
        parcel.writeParcelable(area, flags)
        parcel.writeInt(area_id)
        parcel.writeTypedList(clinic_phones)
        parcel.writeString(closing_time)
        parcel.writeParcelable(doctor, flags)
        parcel.writeInt(doctor_id)
        parcel.writeParcelable(governorate, flags)
        parcel.writeInt(governorate_id)
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(notes)
        parcel.writeString(opening_time)
        parcel.writeString(overview)
        parcel.writeString(reservation_time_end)
        parcel.writeString(reservation_time_start)
        parcel.writeString(status)
        parcel.writeParcelable(user, flags)
        parcel.writeInt(user_id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ClinicData> {
        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        override fun createFromParcel(parcel: Parcel): ClinicData {
            return ClinicData(parcel)
        }

        override fun newArray(size: Int): Array<ClinicData?> {
            return arrayOfNulls(size)
        }
    }
}