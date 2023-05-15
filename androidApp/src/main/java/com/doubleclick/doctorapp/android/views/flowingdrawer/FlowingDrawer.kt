package com.doubleclick.doctorapp.android.views.flowingdrawer

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker

open class FlowingDrawer : ElasticDrawer {
    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!, attrs
    ) {
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context!!, attrs, defStyle
    ) {
    }

    @SuppressLint("NewApi")
    override fun initDrawer(context: Context, attrs: AttributeSet?, defStyle: Int) {
        super.initDrawer(context, attrs, defStyle)
    }

    override fun openMenu(animate: Boolean, y: Float) {
        var animateTo = 0
        when (_position_) {
            Position.LEFT -> animateTo = mMenuSize
            Position.RIGHT -> animateTo = -mMenuSize
        }
        animateOffsetTo(animateTo, 0, animate, y)
    }

    override fun openMenu(animate: Boolean) {
        openMenu(animate, (height / 2).toFloat())
    }

    override fun closeMenu(animate: Boolean) {
        closeMenu(animate, (height / 2).toFloat())
    }

    override fun closeMenu(animate: Boolean, y: Float) {
        animateOffsetTo(0, 0, animate, y)
    }

    @SuppressLint("NewApi")
    override fun onOffsetPixelsChanged(offsetPixels: Int) {
        when (_position_) {
            Position.LEFT -> mMenuContainer!!.translationX = (offsetPixels - mMenuSize).toFloat()
            Position.RIGHT -> mMenuContainer!!.translationX = (offsetPixels + mMenuSize).toFloat()
        }
        invalidate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        onOffsetPixelsChanged(mOffsetPixels.toInt())
    }

    @SuppressLint("NewApi")
    override fun startLayerTranslation() {
        if (mHardwareLayersEnabled && !mLayerTypeHardware) {
            mLayerTypeHardware = true
            mMenuContainer!!.setLayerType(LAYER_TYPE_HARDWARE, null)
        }
    }

    @SuppressLint("NewApi")
    override fun stopLayerTranslation() {
        if (mLayerTypeHardware) {
            mLayerTypeHardware = false
            mMenuContainer!!.setLayerType(LAYER_TYPE_NONE, null)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        check(!(widthMode == MeasureSpec.UNSPECIFIED || heightMode == MeasureSpec.UNSPECIFIED)) { "Must measure with an exact size" }
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        if (mOffsetPixels == -1f) {
            openMenu(false)
        }
        val menuWidthMeasureSpec: Int
        val menuHeightMeasureSpec: Int
        menuWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec, 0, mMenuSize)
        menuHeightMeasureSpec = getChildMeasureSpec(widthMeasureSpec, 0, height)
        mMenuContainer!!.measure(menuWidthMeasureSpec, menuHeightMeasureSpec)
        val contentWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec, 0, width)
        val contentHeightMeasureSpec = getChildMeasureSpec(widthMeasureSpec, 0, height)
        mContentContainer!!.measure(contentWidthMeasureSpec, contentHeightMeasureSpec)
        setMeasuredDimension(width, height)
        updateTouchAreaSize()
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val width = r - l
        val height = b - t
        mContentContainer!!.layout(0, 0, width, height)
        when (_position_) {
            Position.LEFT -> mMenuContainer!!.layout(0, 0, mMenuSize, height)
            Position.RIGHT -> mMenuContainer!!.layout(width - mMenuSize, 0, width, height)
        }
    }

    private fun isContentTouch(x: Int): Boolean {
        var contentTouch = false
        when (_position_) {
            Position.LEFT -> contentTouch = ViewHelper.getRight(mMenuContainer!!) < x
            Position.RIGHT -> contentTouch = ViewHelper.getLeft(mMenuContainer!!) > x
        }
        return contentTouch
    }

    private fun willCloseEnough(): Boolean {
        var closeEnough = false
        when (_position_) {
            Position.LEFT -> closeEnough = mOffsetPixels <= mMenuSize / 2
            Position.RIGHT -> closeEnough = -mOffsetPixels <= mMenuSize / 2
        }
        return closeEnough
    }

    protected fun onDownAllowDrag(): Boolean {
        when (_position_) {
            Position.LEFT -> return (!mMenuVisible && mInitialMotionX <= mTouchSize
                    || mMenuVisible && mInitialMotionX <= mOffsetPixels)
            Position.RIGHT -> {
                val width = width
                val initialMotionX = mInitialMotionX.toInt()
                return (!mMenuVisible && initialMotionX >= width - mTouchSize
                        || mMenuVisible && initialMotionX >= width + mOffsetPixels)
            }
        }
        return false
    }

    protected fun onMoveAllowDrag(x: Int, dx: Float): Boolean {
        if (mMenuVisible && mTouchMode === TOUCH_MODE_FULLSCREEN) {
            return true
        }
        when (_position_) {
            Position.LEFT -> return (!mMenuVisible && mInitialMotionX <= mTouchSize && dx > 0 // Drawer closed
                    || mMenuVisible && x <= mOffsetPixels // Drawer open
                    )
            Position.RIGHT -> {
                val width = width
                return (!mMenuVisible && mInitialMotionX >= width - mTouchSize && dx < 0
                        || mMenuVisible && x >= width + mOffsetPixels)
            }
        }
        return false
    }

    private fun onMoveEvent(dx: Float, y: Float, type: Int) {
        when (_position_) {
            Position.LEFT -> setOffsetPixels(
                Math.min(Math.max(mOffsetPixels + dx, 0f), mMenuSize.toFloat())
                    .toFloat(), y, type
            )
            Position.RIGHT -> setOffsetPixels(
                Math.max(Math.min(mOffsetPixels + dx, 0f), -mMenuSize.toFloat())
                    .toFloat(), y, type
            )
        }
    }

    private fun onUpEvent(x: Int, y: Int) {
        when (_position_) {
            Position.LEFT -> {
                if (mIsDragging) {
                    if (mDrawerState === STATE_DRAGGING_CLOSE) {
                        closeMenu(true, y.toFloat())
                        return
                    }
                    if (mDrawerState === STATE_DRAGGING_OPEN && willCloseEnough()) {
                        smoothClose(y)
                        return
                    }
                    mVelocityTracker!!.computeCurrentVelocity(1000, mMaxVelocity.toFloat())
                    val initialVelocity = getXVelocity(mVelocityTracker!!).toInt()
                    mLastMotionX = x.toFloat()
                    animateOffsetTo(
                        if (initialVelocity > 0) mMenuSize else 0, initialVelocity, true,
                        y.toFloat()
                    )
                } else if (isFirstPointUp) {
                    isFirstPointUp = false
                    return
                } else if (mMenuVisible) {
                    closeMenu(true, y.toFloat())
                }
            }
            Position.RIGHT -> {
                if (mIsDragging) {
                    if (mDrawerState === STATE_DRAGGING_CLOSE) {
                        closeMenu(true, y.toFloat())
                        return
                    }
                    if (mDrawerState === STATE_DRAGGING_OPEN && willCloseEnough()) {
                        smoothClose(y)
                        return
                    }
                    mVelocityTracker!!.computeCurrentVelocity(1000, mMaxVelocity.toFloat())
                    val initialVelocity = getXVelocity(mVelocityTracker!!).toInt()
                    mLastMotionX = x.toFloat()
                    animateOffsetTo(
                        if (initialVelocity > 0) 0 else -mMenuSize, initialVelocity, true,
                        y.toFloat()
                    )
                } else if (isFirstPointUp) {
                    isFirstPointUp = false
                    return
                } else if (mMenuVisible) {
                    closeMenu(true, y.toFloat())
                }
            }
        }
    }

    protected fun checkTouchSlop(dx: Float, dy: Float): Boolean {
        return Math.abs(dx) > mTouchSlop && Math.abs(dx) > Math.abs(dy)
    }

    override fun stopAnimation() {
        super.stopAnimation()
    }

    override fun requestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        super.requestDisallowInterceptTouchEvent(disallowIntercept)
    }

    private fun onPointerUp(ev: MotionEvent) {
        val pointerIndex = ev.actionIndex
        val pointerId = ev.getPointerId(pointerIndex)
        if (pointerId == mActivePointerId) {
            val newPointerIndex = if (pointerIndex == 0) 1 else 0
            mLastMotionX = ev.getX(newPointerIndex)
            mActivePointerId = ev.getPointerId(newPointerIndex)
            if (mVelocityTracker != null) {
                mVelocityTracker!!.clear()
            }
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val action = ev.action and MotionEvent.ACTION_MASK
        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
            mActivePointerId = INVALID_POINTER
            mIsDragging = false
            if (mVelocityTracker != null) {
                mVelocityTracker!!.recycle()
                mVelocityTracker = null
            }
            if (Math.abs(mOffsetPixels) > mMenuSize / 2) {
                openMenu(true, ev.y)
            } else {
                closeMenu(true, ev.y)
            }
            return false
        }
        if (action == MotionEvent.ACTION_DOWN && mMenuVisible && isCloseEnough) {
            setOffsetPixels(0f, 0f, FlowingMenuLayout.TYPE_NONE)
            stopAnimation()
            mDrawerState = STATE_CLOSED
            mIsDragging = false
        }
        // Always intercept events over the content while menu is visible.
        if (mMenuVisible) {
            var index = 0
            if (mActivePointerId !== INVALID_POINTER) {
                index = ev.findPointerIndex(mActivePointerId)
                index = if (index == -1) 0 else index
            }
            val x = ev.getX(index).toInt()
            if (isContentTouch(x)) {
                return true
            }
        }
        if (!mMenuVisible && !mIsDragging && mTouchMode === TOUCH_MODE_NONE) {
            return false
        }
        if (action != MotionEvent.ACTION_DOWN && mIsDragging) {
            return true
        }
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                mInitialMotionX = ev.x
                mLastMotionX = mInitialMotionX
                mInitialMotionY = ev.y
                mLastMotionY = mInitialMotionY
                val allowDrag = onDownAllowDrag()
                mActivePointerId = ev.getPointerId(0)
                if (allowDrag) {
                    mDrawerState = if (mMenuVisible) STATE_OPEN else STATE_CLOSED
                    stopAnimation()
                    mIsDragging = false
                }
            }
            MotionEvent.ACTION_MOVE -> {
                val activePointerId = mActivePointerId
                if (activePointerId == INVALID_POINTER) {
                    // If we don't have a valid id, the touch down wasn't on content.

                }
                val pointerIndex = ev.findPointerIndex(activePointerId)
                if (pointerIndex == -1) {
                    mIsDragging = false
                    mActivePointerId = INVALID_POINTER
                    endDrag()
                    closeMenu(true, ev.y)
                    return false
                }
                val x = ev.getX(pointerIndex)
                val dx = x - mLastMotionX
                val y = ev.getY(pointerIndex)
                val dy = y - mLastMotionY
                if (checkTouchSlop(dx, dy)) {
                    if (mOnInterceptMoveEventListener != null && (mTouchMode === TOUCH_MODE_FULLSCREEN || mMenuVisible)
                        && canChildrenScroll(dx.toInt(), x.toInt(), y.toInt())
                    ) {
                        endDrag()
                        // Release the velocity tracker
                        requestDisallowInterceptTouchEvent(true)
                        return false
                    }
                    val allowDrag = onMoveAllowDrag(x.toInt(), dx)
                    if (allowDrag) {
                        stopAnimation()
                        if (mDrawerState === STATE_OPEN || mDrawerState === STATE_OPENING) {
                            mDrawerState = STATE_DRAGGING_CLOSE
                        } else {
                            mDrawerState = STATE_DRAGGING_OPEN
                        }
                        mIsDragging = true
                        mLastMotionX = x
                        mLastMotionY = y
                    }
                }
            }
            MotionEvent.ACTION_POINTER_UP -> {
                onPointerUp(ev)
                mLastMotionX = ev.getX(ev.findPointerIndex(mActivePointerId))
                mLastMotionY = ev.getY(ev.findPointerIndex(mActivePointerId))
            }
        }
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain()
        }
        mVelocityTracker!!.addMovement(ev)
        return mIsDragging
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        if (!mMenuVisible && !mIsDragging && mTouchMode === TOUCH_MODE_NONE) {
            return false
        }
        val action = ev.action and MotionEvent.ACTION_MASK
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain()
        }
        mVelocityTracker!!.addMovement(ev)
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                mInitialMotionX = ev.x
                mLastMotionX = mInitialMotionX
                mInitialMotionY = ev.y
                mLastMotionY = mInitialMotionY
                val allowDrag = onDownAllowDrag()
                mActivePointerId = ev.getPointerId(0)
                if (allowDrag) {
                    stopAnimation()
                    startLayerTranslation()
                }
            }
            MotionEvent.ACTION_MOVE -> {
                val pointerIndex = ev.findPointerIndex(mActivePointerId)
                if (pointerIndex == -1) {
                    mIsDragging = false
                    mActivePointerId = INVALID_POINTER
                    endDrag()
                    closeMenu(true, ev.y)
                    return false
                }
                if (mIsDragging) {
                    startLayerTranslation()
                    val x = ev.getX(pointerIndex)
                    val dx = x - mLastMotionX
                    val y = ev.getY(pointerIndex)
                    mLastMotionX = x
                    mLastMotionY = y
                    if (mDrawerState === STATE_DRAGGING_OPEN) {
                        if (_position_ === Position.LEFT) {
                            if (mOffsetPixels + dx < mMenuSize / 2) {
                                onMoveEvent(dx, y, FlowingMenuLayout.TYPE_UP_MANUAL)
                            } else {
                                mVelocityTracker!!.computeCurrentVelocity(
                                    1000,
                                    mMaxVelocity.toFloat()
                                )
                                val initialVelocity = getXVelocity(mVelocityTracker!!).toInt()
                                mLastMotionX = x
                                animateOffsetTo(mMenuSize, initialVelocity, true, y)
                                isFirstPointUp = true
                                endDrag()
                            }
                        } else {
                            if (mOffsetPixels + dx > -mMenuSize / 2) {
                                onMoveEvent(dx, y, FlowingMenuLayout.TYPE_UP_MANUAL)
                            } else {
                                mVelocityTracker!!.computeCurrentVelocity(
                                    1000,
                                    mMaxVelocity.toFloat()
                                )
                                val initialVelocity = getXVelocity(mVelocityTracker!!).toInt()
                                mLastMotionX = x
                                animateOffsetTo(-mMenuSize, initialVelocity, true, y)
                                isFirstPointUp = true
                                endDrag()
                            }
                        }
                    } else if (mDrawerState === STATE_DRAGGING_CLOSE) {
                        onMoveEvent(dx, y, FlowingMenuLayout.TYPE_DOWN_MANUAL)
                    }
                }
            }
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                var index = ev.findPointerIndex(mActivePointerId)
                index = if (index == -1) 0 else index
                val x = ev.getX(index).toInt()
                val y = ev.getY(index).toInt()
                onUpEvent(x, y)
                mActivePointerId = INVALID_POINTER
                mIsDragging = false
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                val index = (ev.action and MotionEvent.ACTION_POINTER_INDEX_MASK
                        shr MotionEvent.ACTION_POINTER_INDEX_SHIFT)
                mLastMotionX = ev.getX(index)
                mLastMotionY = ev.getY(index)
                mActivePointerId = ev.getPointerId(index)
            }
            MotionEvent.ACTION_POINTER_UP -> {
                onPointerUp(ev)
                mLastMotionX = ev.getX(ev.findPointerIndex(mActivePointerId))
                mLastMotionY = ev.getY(ev.findPointerIndex(mActivePointerId))
            }
        }
        return true
    }
}
