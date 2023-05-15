package com.doubleclick.doctorapp.android.views.flowingdrawer

import android.annotation.SuppressLint
import android.os.Build
import android.view.View

internal object ViewHelper {
    @SuppressLint("NewApi")
    fun getLeft(v: View): Int {
        return (v.left + v.translationX).toInt()
    }

    @SuppressLint("NewApi")
    fun getTop(v: View): Int {
        return (v.top + v.translationY).toInt()
    }

    @SuppressLint("NewApi")
    fun getRight(v: View): Int {
        return (v.right + v.translationX).toInt()
    }

    @SuppressLint("NewApi")
    fun getLayoutDirection(v: View): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            v.layoutDirection
        } else View.LAYOUT_DIRECTION_LTR
    }
}