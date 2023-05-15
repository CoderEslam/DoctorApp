package com.doubleclick.doctorapp.android.views.SignatureView

import android.content.Context
import android.graphics.*
import android.text.TextUtils
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.ColorInt
import com.doubleclick.doctorapp.android.OnSignatureDone
import com.doubleclick.doctorapp.android.R
import java.io.*


/**
 * Created By Eslam Ghazy on 11/20/2022
 */
class SignatureView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    View(context, attrs, defStyleAttr) {

    private var mPenX = 0f

    private var mPenY = 0f
    private val mPaint = Paint()
    private val mPath = Path()
    private var mCanvas: Canvas? = null
    private var cacheBitmap: Bitmap? = null

    private var mPentWidth = PEN_WIDTH

    private var mPenColor = PEN_COLOR

    private var mBackColor = BACK_COLOR
    private var isTouched = false
    private var mSavePath: String? = null

    private lateinit var onSignatureDone: OnSignatureDone

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SignatureView)
        mPenColor = typedArray.getColor(R.styleable.SignatureView_penColor, PEN_COLOR)
        mBackColor = typedArray.getColor(R.styleable.SignatureView_backColor, BACK_COLOR)
        mPentWidth = typedArray.getInt(R.styleable.SignatureView_penWidth, PEN_WIDTH)
        typedArray.recycle()
        init()
    }

    private fun init() {
        mPaint.isAntiAlias = true
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = mPentWidth.toFloat()
        mPaint.color = mPenColor
    }

    fun getTouched(): Boolean {
        return isTouched
    }

    fun setPentWidth(pentWidth: Int) {
        mPentWidth = pentWidth
    }

    fun setPenColor(@ColorInt penColor: Int) {
        mPenColor = penColor
    }

    fun setBackColor(@ColorInt backColor: Int) {
        mBackColor = backColor
    }


    fun clear() {
        if (mCanvas != null) {
            isTouched = false
            mCanvas!!.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
            mCanvas!!.drawColor(mBackColor)
            invalidate()
        }
    }

    @Throws(IOException::class)
    fun save(path: String?, clearBlank: Boolean, blank: Int) {
        if (TextUtils.isEmpty(path)) {
            return
        }
        mSavePath = path
        var bitmap = cacheBitmap
        if (clearBlank) {
            bitmap = clearBlank(bitmap, blank)
        }
        val bos = ByteArrayOutputStream()
        bitmap!!.compress(Bitmap.CompressFormat.PNG, 100, bos)
        val buffer = bos.toByteArray()
        if (buffer != null) {
            val file = File(path)
            if (file.exists()) {
                file.delete()
            }
            val os: OutputStream = FileOutputStream(file)
            os.write(buffer)
            os.close()
            bos.close()
        }
    }


    fun getBitmap(): Bitmap {
        isDrawingCacheEnabled = true
        buildDrawingCache()
        val bitmap = drawingCache
        isDrawingCacheEnabled = false
        return bitmap
    }


    fun getSavePath(): String? {
        return mSavePath
    }


    private fun clearBlank(bmp: Bitmap?, blank: Int): Bitmap {
        var blank = blank
        val height = bmp!!.height
        val width = bmp.width
        var top = 0
        var left = 0
        var right = 0
        var bottom = 0
        var pixs = IntArray(width)
        var isStop: Boolean

        for (i in 0 until height) {
            bmp.getPixels(pixs, 0, width, 0, i, width, 1)
            isStop = false
            for (pix in pixs) {
                if (pix != mBackColor) {
                    top = i
                    isStop = true
                    break
                }
            }
            if (isStop) {
                break
            }
        }
        for (i in height - 1 downTo 0) {
            bmp.getPixels(pixs, 0, width, 0, i, width, 1)
            isStop = false
            for (pix in pixs) {
                if (pix != mBackColor) {
                    bottom = i
                    isStop = true
                    break
                }
            }
            if (isStop) {
                break
            }
        }
        pixs = IntArray(height)
        for (x in 0 until width) {
            bmp.getPixels(pixs, 0, 1, x, 0, 1, height)
            isStop = false
            for (pix in pixs) {
                if (pix != mBackColor) {
                    left = x
                    isStop = true
                    break
                }
            }
            if (isStop) {
                break
            }
        }
        for (x in width - 1 downTo 1) {
            bmp.getPixels(pixs, 0, 1, x, 0, 1, height)
            isStop = false
            for (pix in pixs) {
                if (pix != mBackColor) {
                    right = x
                    isStop = true
                    break
                }
            }
            if (isStop) {
                break
            }
        }
        if (blank < 0) {
            blank = 0
        }
        left = if (left - blank > 0) left - blank else 0
        top = if (top - blank > 0) top - blank else 0
        right = if (right + blank > width - 1) width - 1 else right + blank
        bottom = if (bottom + blank > height - 1) height - 1 else bottom + blank
        return Bitmap.createBitmap(bmp, left, top, right - left, bottom - top)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        cacheBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        mCanvas = Canvas(cacheBitmap!!)
        mCanvas!!.drawColor(mBackColor)
        isTouched = false
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(cacheBitmap!!, 0f, 0f, mPaint)
        canvas.drawPath(mPath, mPaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mPenX = event.x
                mPenY = event.y
                mPath.moveTo(mPenX, mPenY)
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                isTouched = true
                val x = event.x
                val y = event.y
                val penX = mPenX
                val penY = mPenY
                val dx = Math.abs(x - penX)
                val dy = Math.abs(y - penY)
                if (dx >= 3 || dy >= 3) {
                    val cx = (x + penX) / 2
                    val cy = (y + penY) / 2
                    mPath.quadTo(penX, penY, cx, cy)
                    mPenX = x
                    mPenY = y
                }
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                mCanvas!!.drawPath(mPath, mPaint)
                mPath.reset()
//                onSignatureDone?.onSignatureDone()
            }
            else -> {}
        }
        return super.onTouchEvent(event)
    }

    companion object {
        private val TAG = SignatureView::class.java.simpleName
        const val PEN_WIDTH = 10
        const val PEN_COLOR = Color.BLACK
        const val BACK_COLOR = Color.WHITE
    }

    public fun setOnSignatureDone(onSignatureDone: OnSignatureDone) {
        this.onSignatureDone = onSignatureDone
    }
}