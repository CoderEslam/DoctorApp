package com.doubleclick.doctorapp.android.views.simplecropview.animation

interface SimpleValueAnimatorListener {
    fun onAnimationStarted()
    fun onAnimationUpdated(scale: Float)
    fun onAnimationFinished()
}
