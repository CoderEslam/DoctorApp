package com.doubleclick.doctorapp.android.views.flowingdrawer

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PaintFlagsDrawFilter
import android.os.Build
import android.util.AttributeSet
import android.widget.FrameLayout

open class BuildLayerFrameLayout : FrameLayout {
    private var mChanged = false
    private var mHardwareLayersEnabled = true
    private var mAttached = false
    private var mFirst = true

    constructor(context: Context?) : super(context!!) {
        clipChildren = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            setLayerType(LAYER_TYPE_HARDWARE, null)
        }
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!, attrs
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            setLayerType(LAYER_TYPE_HARDWARE, null)
        }
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context!!, attrs, defStyle
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            setLayerType(LAYER_TYPE_HARDWARE, null)
        }
    }

    fun setHardwareLayersEnabled(enabled: Boolean) {
        mHardwareLayersEnabled = enabled
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        mAttached = true
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mAttached = false
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH && mHardwareLayersEnabled) {
            post {
                mChanged = true
                invalidate()
            }
        }
    }

    override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)
        if (mChanged && Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            post {
                if (mAttached && Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                    val layerType = layerType
                    // If it's already a hardware layer, it'll be built anyway.
                    if (layerType != LAYER_TYPE_HARDWARE || mFirst) {
                        mFirst = false
                        setLayerType(LAYER_TYPE_HARDWARE, null)
                        buildLayer()
                        setLayerType(LAYER_TYPE_NONE, null)
                    }
                }
            }
            mChanged = false
        }
        val pfd = PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
        canvas.drawFilter = pfd
    }
}