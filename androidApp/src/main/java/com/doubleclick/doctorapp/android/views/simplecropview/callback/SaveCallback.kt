package com.doubleclick.doctorapp.android.views.simplecropview.callback

import android.net.Uri

interface SaveCallback : Callback {
    fun onSuccess(uri: Uri?)
}
