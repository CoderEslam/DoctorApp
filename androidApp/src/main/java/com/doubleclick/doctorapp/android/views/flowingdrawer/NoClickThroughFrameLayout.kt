package com.doubleclick.doctorapp.android.views.flowingdrawer

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent

class NoClickThroughFrameLayout : BuildLayerFrameLayout {
    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return true
    }
}