package com.doubleclick.doctorapp.android.views.simplecropview.animation

import android.animation.Animator
import android.animation.ValueAnimator
import android.annotation.TargetApi
import android.os.Build
import android.view.animation.Interpolator

class ValueAnimatorV14(interpolator: Interpolator?) :
    SimpleValueAnimator, Animator.AnimatorListener, ValueAnimator.AnimatorUpdateListener {
    private val animator: ValueAnimator
    private var animatorListener: SimpleValueAnimatorListener =
        object : SimpleValueAnimatorListener {
            override fun onAnimationStarted() {}
            override fun onAnimationUpdated(scale: Float) {}
            override fun onAnimationFinished() {}
        }

    init {
        animator = ValueAnimator.ofFloat(0.0f, 1.0f)
        animator.addListener(this)
        animator.addUpdateListener(this)
        animator.interpolator = interpolator
    }

    override fun startAnimation(duration: Long) {
        if (duration >= 0) {
            animator.duration = duration
        } else {
            animator.duration = DEFAULT_ANIMATION_DURATION.toLong()
        }
        animator.start()
    }

    override fun cancelAnimation() {
        animator.cancel()
    }

    override fun isAnimationStarted(): Boolean {
        return animator.isStarted
    }


    override fun addAnimatorListener(animatorListener: SimpleValueAnimatorListener?) {
        if (animatorListener != null) this.animatorListener = animatorListener
    }

    override fun onAnimationStart(animation: Animator) {
        animatorListener.onAnimationStarted()
    }

    override fun onAnimationEnd(animation: Animator) {
        animatorListener.onAnimationFinished()
    }

    override fun onAnimationCancel(animation: Animator) {
        animatorListener.onAnimationFinished()
    }

    override fun onAnimationRepeat(animation: Animator) {}
    override fun onAnimationUpdate(animation: ValueAnimator) {
        animatorListener.onAnimationUpdated(animation.animatedFraction)
    }

    companion object {
        private const val DEFAULT_ANIMATION_DURATION = 150
    }
}
