package com.doubleclick.doctorapp.android.Model.Patient

import android.os.Parcel
import android.os.Parcelable

data class Data(
    val alcohol_drinking: Int,
    val area: Area?,
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
    val reservations: List<Reservation>?,
    val smoking: Int,
    val status: String?,
    val telephone: String?,
    val user: User?,
    val user_id: Int,
    val visits: List<Visit>?,
    val weight: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readParcelable(Area::class.java.classLoader),
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
        parcel.createTypedArrayList(Reservation),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(User::class.java.classLoader),
        parcel.readInt(),
        parcel.createTypedArrayList(Visit),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(alcohol_drinking)
        parcel.writeParcelable(area, flags)
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
        parcel.writeTypedList(reservations)
        parcel.writeInt(smoking)
        parcel.writeString(status)
        parcel.writeString(telephone)
        parcel.writeParcelable(user, flags)
        parcel.writeInt(user_id)
        parcel.writeTypedList(visits)
        parcel.writeInt(weight)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Data

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }

    companion object CREATOR : Parcelable.Creator<Data> {
        override fun createFromParcel(parcel: Parcel): Data {
            return Data(parcel)
        }

        override fun newArray(size: Int): Array<Data?> {
            return arrayOfNulls(size)
        }
    }
}