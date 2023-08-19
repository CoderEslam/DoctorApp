package com.doubleclick.doctorapp.android.Model.Assistants

import android.os.Parcel
import android.os.Parcelable

data class AssistantsList(
    val `data`: List<AssistantsModel>?
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.createTypedArrayList(AssistantsModel)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(data)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AssistantsList> {
        override fun createFromParcel(parcel: Parcel): AssistantsList {
            return AssistantsList(parcel)
        }

        override fun newArray(size: Int): Array<AssistantsList?> {
            return arrayOfNulls(size)
        }
    }
}