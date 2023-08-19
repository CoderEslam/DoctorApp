package com.doubleclick.doctorapp.android.Model.Favorite

import android.os.Parcel
import android.os.Parcelable

data class FavoriteDoctor(
    val `data`: List<FavoriteModel>?
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.createTypedArrayList(FavoriteModel)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(data)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FavoriteDoctor> {
        override fun createFromParcel(parcel: Parcel): FavoriteDoctor {
            return FavoriteDoctor(parcel)
        }

        override fun newArray(size: Int): Array<FavoriteDoctor?> {
            return arrayOfNulls(size)
        }
    }
}