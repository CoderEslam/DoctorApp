package com.doubleclick.doctorapp.android.views.simplecropview.callback

import android.graphics.Bitmap

interface CropCallback : Callback {
    fun onSuccess(cropped: Bitmap?)
}
