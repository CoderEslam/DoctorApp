package com.doubleclick.doctorapp.android.views.simplecropview.animation

interface SimpleValueAnimator {
    fun startAnimation(duration: Long)
    fun cancelAnimation()
    fun isAnimationStarted(): Boolean

    fun addAnimatorListener(animatorListener: SimpleValueAnimatorListener?)
}
