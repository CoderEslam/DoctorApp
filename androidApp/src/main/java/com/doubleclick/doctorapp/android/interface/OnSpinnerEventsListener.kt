package com.doubleclick.doctorapp.android

import android.widget.Spinner

interface OnSpinnerEventsListener {
    fun onPopupWindowOpened(spinner: Spinner?)
    fun onPopupWindowClosed(spinner: Spinner?)
}