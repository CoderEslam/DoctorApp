package com.doubleclick.doctorapp.android.views.simplecropview.util

import android.util.Log

object Logger {
    private const val TAG = "SimpleCropView"
    var enabled = false
    fun e(msg: String?) {
        if (!enabled) return
        Log.e(TAG, msg!!)
    }

    fun e(msg: String?, e: Throwable?) {
        if (!enabled) return
        Log.e(TAG, msg, e)
    }

    fun i(msg: String?) {
        if (!enabled) return
        Log.i(TAG, msg!!)
    }

    fun i(msg: String?, e: Throwable?) {
        if (!enabled) return
        Log.i(TAG, msg, e)
    }
}
