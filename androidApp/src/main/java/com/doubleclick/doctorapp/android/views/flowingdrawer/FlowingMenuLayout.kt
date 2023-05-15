package com.doubleclick.doctorapp.android.views.flowingdrawer

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Region
import android.os.Build
import android.util.AttributeSet
import android.widget.FrameLayout

class FlowingMenuLayout @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    FrameLayout(context!!, attrs, defStyleAttr) {
    private val mClipPath: Path
    private var mClipOffsetPixels = 0f
    private var currentType = TYPE_NONE
    private var eventY = 0f
    private var topControlY = 0
    private var bottomControlY = 0
    private var topY = 0
    private var bottomY = 0
    private var _width_ = 0
    private var _height_ = 0
    private var verticalOffsetRatio = 0.0
    private var ratio1 = 0.0
    private var ratio2 = 0.0
    private var fraction = 0f
    private var fractionUpDown = 0f
    private var fractionEdge = 0f
    private var fractionCenter = 0f
    private var fractionCenterDown = 0f
    private var centerXOffset = 0
    private var edgeXOffset = 0
    private val mPaint: Paint
    private var position = 0

    init {
        mClipPath = Path()
        mPaint = Paint()
        mPaint.isAntiAlias = true
        mPaint.style = Paint.Style.FILL
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2
            && Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB
        ) {
            setLayerType(LAYER_TYPE_SOFTWARE, mPaint)
        }
    }

    fun setPaintColor(color: Int) {
        mPaint.color = color
    }

    fun setMenuPosition(position: Int) {
        this.position = position
    }

    fun setClipOffsetPixels(clipOffsetPixels: Float, eventY: Float, type: Int) {
        mClipOffsetPixels = clipOffsetPixels
        currentType = type
        this.eventY = eventY
        invalidate()
    }

    fun setUpDownFraction(fraction: Float) {
        fractionUpDown = fraction
        currentType = TYPE_UP_DOWN
        invalidate()
    }

    override fun dispatchDraw(canvas: Canvas) {
        _width_ = width
        _height_ = height
        mClipPath.reset()
        if (position == ElasticDrawer.Position.LEFT) {
            drawLeftMenu()
        } else {
            drawRightMenu()
        }
        canvas.save()
        canvas.drawPath(mClipPath, mPaint)
        canvas.clipPath(mClipPath, Region.Op.INTERSECT)
        super.dispatchDraw(canvas)
        canvas.restore()
    }

    private fun drawLeftMenu() {
        when (currentType) {
            TYPE_NONE -> {
                /**
                 * 空状态
                 * mClipOffsetPixels =0 or mClipOffsetPixels = width
                 */
                mClipPath.moveTo(0f, 0f)
                mClipPath.lineTo(_width_.toFloat(), 0f)
                mClipPath.lineTo(_width_.toFloat(), _height_.toFloat())
                mClipPath.lineTo(0f, _height_.toFloat())
                mClipPath.lineTo(0f, 0f)
            }
            TYPE_UP_MANUAL -> {
                /**
                 * 手动打开状态
                 * verticalOffsetRatio = 0 when currentPointY = 0.5 * height ;
                 * verticalOffsetRatio = 1 when currentPointY = height or currentPointY = 0;
                 * bottomY,topY由两部分组成
                 * 第一部分是初始位置，由ratio1和currentPointY决定
                 * 第二部分由currentPointX移动位置决定
                 * 两部分系数分别是ratio1，ratio2
                 * ratio1，ratio2表示 (currentPointY - topY)/ (bottomY - currentPointY)
                 * 第一部分bottomY - topY的初始值为height的0.7 倍
                 * 第二部分bottomY - topY变化的总长为currentPointX变化总长的6倍
                 */
                verticalOffsetRatio = Math.abs((2 * eventY - _height_).toDouble() / _height_)
                ratio1 = verticalOffsetRatio * 3 + 1
                ratio2 = verticalOffsetRatio * 5 + 1
                if (eventY - _height_ / 2 >= 0) {
                    bottomY =
                        (eventY + 0.7 * _height_ / (ratio1 + 1) + mClipOffsetPixels * 6 / (ratio2 + 1)).toInt()
                    topY =
                        (eventY - 0.7 * _height_ / (1 + 1 / ratio1) - mClipOffsetPixels * 6 / (1 / ratio2 + 1)).toInt()
                    topControlY = (-bottomY / 4 + 5 * eventY / 4).toInt()
                    bottomControlY = (bottomY / 4 + 3 * eventY / 4).toInt()
                } else {
                    bottomY =
                        (eventY + 0.7 * _height_ / (1 / ratio1 + 1) + mClipOffsetPixels * 6 / (1 / ratio2 + 1)).toInt()
                    topY =
                        (eventY - 0.7 * _height_ / (1 + ratio1) - mClipOffsetPixels * 6 / (ratio2 + 1)).toInt()
                    topControlY = (topY / 4 + 3 * eventY / 4).toInt()
                    bottomControlY = (-topY / 4 + 5 * eventY / 4).toInt()
                }
                mClipPath.moveTo(_width_ - mClipOffsetPixels, topY.toFloat())
                mClipPath.cubicTo(
                    _width_ - mClipOffsetPixels, topControlY.toFloat(), _width_.toFloat(),
                    topControlY.toFloat(), _width_.toFloat(), eventY
                )
                mClipPath.cubicTo(
                    _width_.toFloat(), bottomControlY.toFloat(), _width_ - mClipOffsetPixels,
                    bottomControlY.toFloat(), _width_ - mClipOffsetPixels, bottomY.toFloat()
                )
                mClipPath.lineTo(_width_ - mClipOffsetPixels, topY.toFloat())
            }
            TYPE_UP_AUTO -> {
                /**
                 * 自动打开状态
                 * fraction变化范围是0-1
                 * 0-0.5时fractionCenter变化慢（根号函数）,fractionEdge变化快（指数函数）
                 * 0.5-1时fractionCenter变化快（指数函数）,fractionEdge变化慢（根号函数）
                 * centerXOffset初始值width / 2, 变化到width + 150
                 * edgeXOffset初始值width * 0.75 ,变化到width + 100
                 */
                fraction = (mClipOffsetPixels - _width_ / 2) / (_width_ / 2)
                if (fraction <= 0.5) {
                    fractionCenter = (2 * Math.pow(fraction.toDouble(), 2.0)).toFloat()
                    fractionEdge = (1 / Math.sqrt(2.0) * Math.sqrt(fraction.toDouble())).toFloat()
                } else {
                    fractionCenter =
                        (1 / (2 - Math.sqrt(2.0)) * Math.sqrt(fraction.toDouble()) + 1 - 1 / (2 - Math.sqrt(
                            2.0
                        ))).toFloat()
                    fractionEdge = (2 * Math.pow(fraction.toDouble(), 2.0) / 3 + 1f / 3).toFloat()
                }
                centerXOffset = (_width_ / 2 + fractionCenter * (_width_ / 2 + 150)).toInt()
                edgeXOffset = (_width_ * 0.75 + fractionEdge * (_width_ / 4 + 100)).toInt()
                mClipPath.moveTo(_width_ - mClipOffsetPixels, 0f)
                mClipPath.lineTo(edgeXOffset.toFloat(), 0f)
                mClipPath.quadTo(
                    centerXOffset.toFloat(),
                    eventY,
                    edgeXOffset.toFloat(),
                    _height_.toFloat()
                )
                mClipPath.lineTo(_width_ - mClipOffsetPixels, _height_.toFloat())
                mClipPath.lineTo(_width_ - mClipOffsetPixels, 0f)
            }
            TYPE_UP_DOWN -> {
                /**
                 * 打开后回弹状态
                 * centerXOffset初始值width + 150,变化到width
                 * edgeXOffset初始值width + 100 ,变化到width
                 */
                centerXOffset = (_width_ + 150 - 150 * fractionUpDown).toInt()
                edgeXOffset = (_width_ + 100 - 100 * fractionUpDown).toInt()
                mClipPath.moveTo(_width_ - mClipOffsetPixels, 0f)
                mClipPath.lineTo(edgeXOffset.toFloat(), 0f)
                mClipPath.quadTo(
                    centerXOffset.toFloat(),
                    eventY,
                    edgeXOffset.toFloat(),
                    _height_.toFloat()
                )
                mClipPath.lineTo(_width_ - mClipOffsetPixels, _height_.toFloat())
                mClipPath.lineTo(_width_ - mClipOffsetPixels, 0f)
            }
            TYPE_DOWN_AUTO -> {
                /**
                 * 自动关闭状态
                 * edgeXOffset值width
                 * centerXOffset 比edgeXOffset多移动0.5 * width
                 */
                fractionCenterDown = 1 - mClipOffsetPixels / _width_
                centerXOffset = (_width_ - 0.5 * _width_ * fractionCenterDown).toInt()
                mClipPath.moveTo(_width_ - mClipOffsetPixels, 0f)
                mClipPath.lineTo(_width_.toFloat(), 0f)
                mClipPath.quadTo(centerXOffset.toFloat(), eventY, _width_.toFloat(), _height_.toFloat())
                mClipPath.lineTo(_width_ - mClipOffsetPixels, _height_.toFloat())
                mClipPath.lineTo(_width_ - mClipOffsetPixels, 0f)
            }
            TYPE_DOWN_MANUAL -> {
                /**
                 * 手动关闭状态
                 * edgeXOffset值width
                 * centerXOffset 比edgeXOffset多移动0.5 * width
                 */
                fractionCenterDown = 1 - mClipOffsetPixels / _width_
                centerXOffset = (_width_ - 0.5 * _width_ * fractionCenterDown).toInt()
                mClipPath.moveTo(_width_ - mClipOffsetPixels, 0f)
                mClipPath.lineTo(_width_.toFloat(), 0f)
                mClipPath.quadTo(centerXOffset.toFloat(), eventY, _width_.toFloat(), _height_.toFloat())
                mClipPath.lineTo(_width_ - mClipOffsetPixels, _height_.toFloat())
                mClipPath.lineTo(_width_ - mClipOffsetPixels, 0f)
            }
            TYPE_DOWN_SMOOTH -> {
                /**
                 * 手动打开不到一半,松手后恢复到初始状态
                 * 每次绘制两边纵坐标增加10
                 */
                bottomY = bottomY + 10
                topY = topY - 10
                if (eventY - _height_ / 2 >= 0) {
                    topControlY = (-bottomY / 4 + 5 * eventY / 4).toInt()
                    bottomControlY = (bottomY / 4 + 3 * eventY / 4).toInt()
                } else {
                    topControlY = (topY / 4 + 3 * eventY / 4).toInt()
                    bottomControlY = (-topY / 4 + 5 * eventY / 4).toInt()
                }
                mClipPath.moveTo(_width_ - mClipOffsetPixels, topY.toFloat())
                mClipPath.cubicTo(
                    _width_ - mClipOffsetPixels, topControlY.toFloat(), _width_.toFloat(),
                    topControlY.toFloat(), _width_.toFloat(), eventY
                )
                mClipPath.cubicTo(
                    _width_.toFloat(), bottomControlY.toFloat(), _width_ - mClipOffsetPixels,
                    bottomControlY.toFloat(), _width_ - mClipOffsetPixels, bottomY.toFloat()
                )
                mClipPath.lineTo(_width_ - mClipOffsetPixels, topY.toFloat())
            }
            else -> {}
        }
    }

    private fun drawRightMenu() {
        when (currentType) {
            TYPE_NONE -> {
                /**
                 * 空状态
                 * mClipOffsetPixels =0 or mClipOffsetPixels = width
                 */
                mClipPath.moveTo(_width_.toFloat(), 0f)
                mClipPath.lineTo(0f, 0f)
                mClipPath.lineTo(0f, _height_.toFloat())
                mClipPath.lineTo(_width_.toFloat(), _height_.toFloat())
                mClipPath.lineTo(_width_.toFloat(), 0f)
            }
            TYPE_UP_MANUAL -> {
                /**
                 * 手动打开状态
                 * verticalOffsetRatio = 0 when currentPointY = 0.5 * height ;
                 * verticalOffsetRatio = 1 when currentPointY = height or currentPointY = 0;
                 * bottomY,topY由两部分组成
                 * 第一部分是初始位置，由ratio1和currentPointY决定
                 * 第二部分由currentPointX移动位置决定
                 * 两部分系数分别是ratio1，ratio2
                 * ratio1，ratio2表示 (currentPointY - topY)/ (bottomY - currentPointY)
                 * 第一部分bottomY - topY的初始值为height的0.7 倍
                 * 第二部分bottomY - topY变化的总长为currentPointX变化总长的6倍
                 */
                verticalOffsetRatio = Math.abs((2 * eventY - _height_).toDouble() / _height_)
                ratio1 = verticalOffsetRatio * 3 + 1
                ratio2 = verticalOffsetRatio * 5 + 1
                if (eventY - _height_ / 2 >= 0) {
                    bottomY =
                        (eventY + 0.7 * _height_ / (ratio1 + 1) - mClipOffsetPixels * 6 / (ratio2 + 1)).toInt()
                    topY =
                        (eventY - 0.7 * _height_ / (1 + 1 / ratio1) + mClipOffsetPixels * 6 / (1 / ratio2 + 1)).toInt()
                    topControlY = (-bottomY / 4 + 5 * eventY / 4).toInt()
                    bottomControlY = (bottomY / 4 + 3 * eventY / 4).toInt()
                } else {
                    bottomY =
                        (eventY + 0.7 * _height_ / (1 / ratio1 + 1) - mClipOffsetPixels * 6 / (1 / ratio2 +
                                1)).toInt()
                    topY =
                        (eventY - 0.7 * _height_ / (1 + ratio1) + mClipOffsetPixels * 6 / (ratio2 + 1)).toInt()
                    topControlY = (topY / 4 + 3 * eventY / 4).toInt()
                    bottomControlY = (-topY / 4 + 5 * eventY / 4).toInt()
                }
                mClipPath.moveTo(-mClipOffsetPixels, topY.toFloat())
                mClipPath.cubicTo(
                    -mClipOffsetPixels, topControlY.toFloat(), 0f,
                    topControlY.toFloat(), 0f, eventY
                )
                mClipPath.cubicTo(
                    0f, bottomControlY.toFloat(), -mClipOffsetPixels,
                    bottomControlY.toFloat(), -mClipOffsetPixels, bottomY.toFloat()
                )
                mClipPath.lineTo(-mClipOffsetPixels, topY.toFloat())
            }
            TYPE_UP_AUTO -> {
                /**
                 * 自动打开状态
                 * fraction变化范围是0-1
                 * 0-0.5时fractionCenter变化慢（根号函数）,fractionEdge变化快（指数函数）
                 * 0.5-1时fractionCenter变化快（指数函数）,fractionEdge变化慢（根号函数）
                 * centerXOffset初始值width / 2, 变化到width + 150
                 * edgeXOffset初始值width * 0.75 ,变化到width + 100
                 */
                fraction = (-mClipOffsetPixels - _width_ / 2) / (_width_ / 2)
                if (fraction <= 0.5) {
                    fractionCenter = (2 * Math.pow(fraction.toDouble(), 2.0)).toFloat()
                    fractionEdge = (1 / Math.sqrt(2.0) * Math.sqrt(fraction.toDouble())).toFloat()
                } else {
                    fractionCenter =
                        (1 / (2 - Math.sqrt(2.0)) * Math.sqrt(fraction.toDouble()) + 1 - 1 / (2 - Math.sqrt(
                            2.0
                        ))).toFloat()
                    fractionEdge = (2 * Math.pow(fraction.toDouble(), 2.0) / 3 + 1f / 3).toFloat()
                }
                centerXOffset = (_width_ / 2 + fractionCenter * (_width_ / 2 + 150)).toInt()
                edgeXOffset = (_width_ * 0.75 + fractionEdge * (_width_ / 4 + 100)).toInt()
                mClipPath.moveTo(-mClipOffsetPixels, 0f)
                mClipPath.lineTo((_width_ - edgeXOffset).toFloat(), 0f)
                mClipPath.quadTo(
                    (_width_ - centerXOffset).toFloat(),
                    eventY,
                    (_width_ - edgeXOffset).toFloat(),
                    _height_.toFloat()
                )
                mClipPath.lineTo(-mClipOffsetPixels, _height_.toFloat())
                mClipPath.lineTo(-mClipOffsetPixels, 0f)
            }
            TYPE_UP_DOWN -> {
                /**
                 * 打开后回弹状态
                 * centerXOffset初始值width + 150,变化到width
                 * edgeXOffset初始值width + 100 ,变化到width
                 */
                centerXOffset = (_width_ + 150 - 150 * fractionUpDown).toInt()
                edgeXOffset = (_width_ + 100 - 100 * fractionUpDown).toInt()
                mClipPath.moveTo(-mClipOffsetPixels, 0f)
                mClipPath.lineTo((_width_ - edgeXOffset).toFloat(), 0f)
                mClipPath.quadTo(
                    (_width_ - centerXOffset).toFloat(),
                    eventY,
                    (_width_ - edgeXOffset).toFloat(),
                    _height_.toFloat()
                )
                mClipPath.lineTo(-mClipOffsetPixels, _height_.toFloat())
                mClipPath.lineTo(-mClipOffsetPixels, 0f)
            }
            TYPE_DOWN_AUTO -> {
                /**
                 * 自动关闭状态
                 * edgeXOffset值width
                 * centerXOffset 比edgeXOffset多移动0.5 * width
                 */
                fractionCenterDown = 1 + mClipOffsetPixels / _width_
                centerXOffset = (_width_ - 0.5 * _width_ * fractionCenterDown).toInt()
                mClipPath.moveTo(-mClipOffsetPixels, 0f)
                mClipPath.lineTo(0f, 0f)
                mClipPath.quadTo((_width_ - centerXOffset).toFloat(), eventY, 0f, _height_.toFloat())
                mClipPath.lineTo(-mClipOffsetPixels, _height_.toFloat())
                mClipPath.lineTo(-mClipOffsetPixels, 0f)
            }
            TYPE_DOWN_MANUAL -> {
                /**
                 * 手动关闭状态
                 * edgeXOffset值width
                 * centerXOffset 比edgeXOffset多移动0.5 * width
                 */
                fractionCenterDown = 1 + mClipOffsetPixels / _width_
                centerXOffset = (_width_ - 0.5 * _width_ * fractionCenterDown).toInt()
                mClipPath.moveTo(-mClipOffsetPixels, 0f)
                mClipPath.lineTo(0f, 0f)
                mClipPath.quadTo((_width_ - centerXOffset).toFloat(), eventY, 0f, _height_.toFloat())
                mClipPath.lineTo(-mClipOffsetPixels, _height_.toFloat())
                mClipPath.lineTo(-mClipOffsetPixels, 0f)
            }
            TYPE_DOWN_SMOOTH -> {
                /**
                 * 手动打开不到一半,松手后恢复到初始状态
                 * 每次绘制两边纵坐标增加10
                 */
                bottomY = bottomY + 10
                topY = topY - 10
                if (eventY - _height_ / 2 >= 0) {
                    topControlY = (-bottomY / 4 + 5 * eventY / 4).toInt()
                    bottomControlY = (bottomY / 4 + 3 * eventY / 4).toInt()
                } else {
                    topControlY = (topY / 4 + 3 * eventY / 4).toInt()
                    bottomControlY = (-topY / 4 + 5 * eventY / 4).toInt()
                }
                mClipPath.moveTo(-mClipOffsetPixels, topY.toFloat())
                mClipPath.cubicTo(
                    -mClipOffsetPixels, topControlY.toFloat(), 0f,
                    topControlY.toFloat(), 0f, eventY
                )
                mClipPath.cubicTo(
                    0f, bottomControlY.toFloat(), -mClipOffsetPixels,
                    bottomControlY.toFloat(), -mClipOffsetPixels, bottomY.toFloat()
                )
                mClipPath.lineTo(-mClipOffsetPixels, topY.toFloat())
            }
            else -> {}
        }
    }

    companion object {
        const val TYPE_NONE = 0
        const val TYPE_UP_MANUAL = 1
        const val TYPE_UP_AUTO = 2
        const val TYPE_UP_DOWN = 3
        const val TYPE_DOWN_AUTO = 4
        const val TYPE_DOWN_MANUAL = 5
        const val TYPE_DOWN_SMOOTH = 6
    }
}
