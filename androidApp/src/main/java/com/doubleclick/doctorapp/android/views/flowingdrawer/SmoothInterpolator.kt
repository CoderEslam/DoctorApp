package com.doubleclick.doctorapp.android.views.flowingdrawer

import android.view.animation.Interpolator

internal class SmoothInterpolator : Interpolator {
    override fun getInterpolation(t: Float): Float {
        var t = t
        t -= 1.0f
        return t * t * t * t * t + 1.0f
    }
}