package com.doubleclick.doctorapp.android.views.shadowimageview

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.annotation.ColorInt
import androidx.palette.graphics.Palette
import com.doubleclick.doctorapp.android.R


class ShadowImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    RelativeLayout(context, attrs, defStyleAttr) {
    private var shadowRound = 0
    private var shadowColor = -147483648
    private var mInvalidat = false

    init {
        initView(context, attrs)
    }

    private fun initView(context: Context, attrs: AttributeSet?) {
        setPadding(10, 10, 10, 10)
        gravity = Gravity.CENTER
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        var imageresource = -1
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.ShadowImageView)
            if (a.hasValue(R.styleable.ShadowImageView_shadowSrc)) {
                imageresource = a.getResourceId(R.styleable.ShadowImageView_shadowSrc, -1)
            }
            shadowRound =
                a.getDimensionPixelSize(R.styleable.ShadowImageView_shadowRound, shadowRound)
            if (a.hasValue(R.styleable.ShadowImageView_shadowColor)) {
                shadowColor =
                    a.getColor(R.styleable.ShadowImageView_shadowColor, Color.parseColor("#8D8D8D"))
            }
        } else {
            val density = context.resources.displayMetrics.density
            shadowRound = (shadowRound * density).toInt()
            imageresource = -1
        }
        val roundImageView = RoundImageView(context)
        roundImageView.scaleType = ImageView.ScaleType.FIT_XY
        if (imageresource == -1) {
            roundImageView.setImageResource(android.R.color.transparent)
        } else {
            roundImageView.setImageResource(imageresource)
        }
        if (shadowColor == Color.parseColor("#8D8D8D")) {
            shadowColor = -147483648
        }
        addView(roundImageView)
        viewTreeObserver.addOnGlobalLayoutListener {
            var N = childCount
            for (i in 0 until N) {
                val view = getChildAt(i)
                if (i != 0) {
                    removeView(view)
                    childCount
                    continue
                }
                N = childCount
            }
            (getChildAt(0) as RoundImageView).setRound(shadowRound)
            mInvalidat = true
        }
    }

    fun setImageResource(resId: Int) {
        (getChildAt(0) as RoundImageView).setImageResource(resId)
        invalidate()
        mInvalidat = true
    }

    fun setImageDrawable(drawable: Drawable?) {
        (getChildAt(0) as RoundImageView).setImageDrawable(drawable)
        invalidate()
        mInvalidat = true
    }

    fun setImageBitmap(bitmap: Bitmap?) {
        (getChildAt(0) as RoundImageView).setImageBitmap(bitmap)
        invalidate()
        mInvalidat = true
    }

    fun setImageShadowColor(@ColorInt color: Int) {
        shadowColor = color
    }

    fun setImageRadius(radius: Int) {
        var radius = radius
        if (radius > getChildAt(0).width / 2 || radius > getChildAt(0).height / 2) {
            radius = if (getChildAt(0).width > getChildAt(0).height) {
                getChildAt(0).height / 2
            } else {
                getChildAt(0).width / 2
            }
        }
        shadowRound = radius
        (getChildAt(0) as RoundImageView).setRound(shadowRound)
        invalidate()
        mInvalidat = true
    }

    override fun dispatchDraw(canvas: Canvas) {
        if (mInvalidat) {
            mInvalidat = false
            val view = getChildAt(0)
            val shadowPaint = Paint()
            shadowPaint.color = Color.WHITE
            shadowPaint.style = Paint.Style.FILL
            shadowPaint.isAntiAlias = true
            val radius = if (view.height / 12 > 40) 40 else view.height / 12
            val shadowDimen = if (view.height / 16 > 28) 28 else view.height / 16
            val bitmap: Bitmap
            var rgb: Int
            if ((view as ImageView).drawable is ColorDrawable) {
                rgb = (view.drawable as ColorDrawable).color
                shadowPaint.setShadowLayer(40f, 0f, 28f, getDarkerColor(rgb))
            } else if (view.drawable is BitmapDrawable) {
                bitmap = (view.drawable as BitmapDrawable).bitmap
                val mSwatch: Palette.Swatch? = Palette.from(bitmap).generate().dominantSwatch
                rgb = if (null != mSwatch) {
                    mSwatch.getRgb()
                } else {
                    Color.parseColor("#8D8D8D")
                }
                shadowPaint.setShadowLayer(
                    radius.toFloat(),
                    0f,
                    shadowDimen.toFloat(),
                    getDarkerColor(rgb)
                )
                val bitmapT = Bitmap.createBitmap(
                    bitmap, 0, bitmap.height / 4 * 3,
                    bitmap.width, bitmap.height / 4
                )
                if (null != Palette.from(bitmapT).generate().dominantSwatch) {
                    rgb = Palette.from(bitmapT).generate().dominantSwatch!!.rgb
                    shadowPaint.setShadowLayer(radius.toFloat(), 0f, shadowDimen.toFloat(), rgb)
                }
            } else {
                rgb = Color.parseColor("#8D8D8D")
                shadowPaint.setShadowLayer(
                    radius.toFloat(),
                    0f,
                    shadowDimen.toFloat(),
                    getDarkerColor(rgb)
                )
            }
            if (shadowColor != -147483648) {
                shadowPaint.setShadowLayer(radius.toFloat(), 0f, shadowDimen.toFloat(), shadowColor)
            }
            val rectF = RectF(
                view.getX() + view.getWidth() / 20,
                view.getY(),
                view.getX() + view.getWidth() - view.getWidth() / 20,
                view.getY() + view.getHeight() - view.getHeight() / 40
            )
            canvas.drawRoundRect(rectF, shadowRound.toFloat(), shadowRound.toFloat(), shadowPaint)
            canvas.save()
        }
        super.dispatchDraw(canvas)
    }

    fun getDarkerColor(color: Int): Int {
        val hsv = FloatArray(3)
        Color.colorToHSV(color, hsv)
        hsv[1] = hsv[1] + 0.1f
        hsv[2] = hsv[2] - 0.1f
        return Color.HSVToColor(hsv)
    }
}
