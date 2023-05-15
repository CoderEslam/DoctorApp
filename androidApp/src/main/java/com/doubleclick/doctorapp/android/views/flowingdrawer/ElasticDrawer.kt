package com.doubleclick.doctorapp.android.views.flowingdrawer

import android.annotation.SuppressLint
import android.app.ApplicationErrorReport.TYPE_NONE
import android.content.Context
import android.graphics.Canvas
import android.os.Build
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.view.VelocityTracker
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.view.animation.Interpolator
import android.view.animation.OvershootInterpolator
import android.widget.Scroller
import com.doubleclick.doctorapp.android.R
import com.nineoldandroids.animation.Animator
import com.nineoldandroids.animation.ValueAnimator

abstract class ElasticDrawer : ViewGroup {
    /**
     * Indicates whether the menu is currently visible.
     *
     * @return True if the menu is open, false otherwise.
     */
    /**
     * Indicates whether the menu is currently visible.
     */
    protected var mMenuVisible = false

    /**
     * The size of the menu (width or height depending on the gravity).
     */
    protected var mMenuSize = 0

    /**
     * The touch bezel size of the drawer in px.
     */
    var touchBezelSize = 0

    /**
     * Slop before starting a drag.
     */
    protected var mTouchSlop = 0

    /**
     * Maximum velocity allowed when animating the drawer open/closed.
     */
    protected var mMaxVelocity = 0

    /**
     * Scroller used when animating the drawer open/closed.
     */
    private var mScroller: Scroller? = null

    /**
     * Indicates whether the current layer type is [View.LAYER_TYPE_HARDWARE].
     */
    protected var mLayerTypeHardware = false

    /**
     * Indicates whether to use [View.LAYER_TYPE_HARDWARE] when animating the drawer.
     */
    protected var mHardwareLayersEnabled = false

    /**
     * The initial X position of a drag.
     */
    protected var mInitialMotionX = 0f

    /**
     * The initial Y position of a drag.
     */
    protected var mInitialMotionY = 0f

    /**
     * The last X position of a drag.
     */
    protected var mLastMotionX = -1f

    /**
     * The last Y position of a drag.
     */
    protected var mLastMotionY = -1f

    /**
     * Velocity tracker used when animating the drawer open/closed after a drag.
     */
    protected var mVelocityTracker: VelocityTracker? = null

    /**
     * Distance in px from closed position from where the drawer is considered closed with regards to touch events.
     */
    protected var mCloseEnough = 0

    /**
     * The position of the drawer.
     */
    private var mPosition = 0
    private var mResolvedPosition = 0

    /**
     * Touch mode for the Drawer.
     * Possible values are [.TOUCH_MODE_NONE], [.TOUCH_MODE_BEZEL] or [.TOUCH_MODE_FULLSCREEN]
     * Default: [.TOUCH_MODE_BEZEL]
     */
    protected var mTouchMode = TOUCH_MODE_BEZEL

    /**
     * The touch area size of the drawer in px.
     */
    protected var mTouchSize = 0

    /**
     * The parent of the menu view.
     */
    protected var mMenuContainer: BuildLayerFrameLayout? = null

    /**
     * The parent of the content view.
     */
    protected var mContentContainer: BuildLayerFrameLayout? = null

    /**
     * The custom menu view set by the user.
     */
    private var mMenuView: FlowingMenuLayout? = null

    /**
     * The color of the menu.
     */
    protected var mMenuBackground = 0

    /**
     * Current offset.
     */
    protected var mOffsetPixels = 0f

    /**
     * Listener used to dispatch state change events.
     */
    private var mOnDrawerStateChangeListener: OnDrawerStateChangeListener? = null

    /**
     * Callback that lets the listener override intercepting of touch events.
     */
    protected var mOnInterceptMoveEventListener: OnInterceptMoveEventListener? = null

    /**
     * The maximum duration of open/close animations.
     */
    protected var mMaxAnimationDuration = DEFAULT_ANIMATION_DURATION

    /**
     * The current drawer state.
     *
     * @see .STATE_CLOSED
     *
     * @see .STATE_CLOSING
     *
     * @see .STATE_DRAGGING_OPEN
     *
     * @see .STATE_DRAGGING_CLOSE
     *
     * @see .STATE_OPENING
     *
     * @see .STATE_OPEN
     */
    protected var mDrawerState = STATE_CLOSED

    /**
     * Bundle used to hold the drawers state.
     */
    protected var mState: Bundle? = null

    /**
     * Indicates whether the drawer is currently being dragged.
     */
    protected var mIsDragging = false

    /**
     * The current pointer id.
     */
    protected var mActivePointerId = INVALID_POINTER
    private var eventY = 0f
    protected var isFirstPointUp = false

    /**
     * Runnable used when animating the drawer open/closed.
     */
    private val mDragRunnable = Runnable { postAnimationInvalidate() }

    constructor(context: Context?) : super(context) {}

    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyle: Int = R.attr.elasticDrawerStyle
    ) : super(context, attrs, defStyle) {
        initDrawer(context, attrs, defStyle)
    }

    @SuppressLint("NewApi")
    protected open fun initDrawer(context: Context, attrs: AttributeSet?, defStyle: Int) {
        setWillNotDraw(false)
        isFocusable = false
        val a = context.obtainStyledAttributes(attrs, R.styleable.ElasticDrawer)
        mMenuSize = a.getDimensionPixelSize(R.styleable.ElasticDrawer_edMenuSize, dpToPx(240))
        mMenuBackground = a.getColor(R.styleable.ElasticDrawer_edMenuBackground, -0x222223)
        touchBezelSize = a.getDimensionPixelSize(
            R.styleable.ElasticDrawer_edTouchBezelSize,
            dpToPx(DEFAULT_DRAG_BEZEL_DP)
        )
        mMaxAnimationDuration =
            a.getInt(R.styleable.ElasticDrawer_edMaxAnimationDuration, DEFAULT_ANIMATION_DURATION)
        val position = a.getInt(R.styleable.ElasticDrawer_edPosition, 0)
        _position_ = position
        a.recycle()
        mMenuContainer = NoClickThroughFrameLayout(context)
        mMenuContainer!!.setBackgroundColor(resources.getColor(android.R.color.transparent))
        mContentContainer = NoClickThroughFrameLayout(context)
        val configuration = ViewConfiguration.get(context)
        mTouchSlop = configuration.scaledTouchSlop
        mMaxVelocity = configuration.scaledMaximumFlingVelocity
        mScroller = Scroller(context, SMOOTH_INTERPOLATOR)
        mCloseEnough = dpToPx(CLOSE_ENOUGH)
        mContentContainer!!.setLayerType(LAYER_TYPE_NONE, null)
        mContentContainer!!.setHardwareLayersEnabled(false)
    }

    protected fun dpToPx(dp: Int): Int {
        return (resources.displayMetrics.density * dp + 0.5f).toInt()
    }

    /**
     * Callback interface for changing state of the drawer.
     */
    interface OnDrawerStateChangeListener {
        /**
         * Called when the drawer state changes.
         *
         * @param oldState The old drawer state.
         * @param newState The new drawer state.
         */
        fun onDrawerStateChange(oldState: Int, newState: Int)

        /**
         * Called when the drawer slides.
         *
         * @param openRatio    Ratio for how open the menu is.
         * @param offsetPixels Current offset of the menu in pixels.
         */
        fun onDrawerSlide(openRatio: Float, offsetPixels: Int)
    }

    /**
     * Callback that is invoked when the drawer is in the process of deciding whether it should intercept the touch
     * event. This lets the listener decide if the pointer is on a view that would disallow dragging of the drawer.
     * This is only called when the touch mode is [.TOUCH_MODE_FULLSCREEN].
     */
    interface OnInterceptMoveEventListener {
        /**
         * Called for each child the pointer i on when the drawer is deciding whether to intercept the touch event.
         *
         * @param v     View to test for draggability
         * @param delta Delta drag in pixels
         * @param x     X coordinate of the active touch point
         * @param y     Y coordinate of the active touch point
         * @return true if view is draggable by delta dx.
         */
        fun isViewDraggable(v: View?, delta: Int, x: Int, y: Int): Boolean
    }

    internal object Position {
        // Positions the drawer to the left of the content.
        const val LEFT = 1

        // Positions the drawer to the right of the content.
        const val RIGHT = 2

        /**
         * Position the drawer at the start edge. This will position the drawer to the [.LEFT] with LTR
         * languages and
         * [.RIGHT] with RTL languages.
         */
        const val START = 3

        /**
         * Position the drawer at the end edge. This will position the drawer to the [.RIGHT] with LTR
         * languages and
         * [.LEFT] with RTL languages.
         */
        const val END = 4
    }

    protected fun updateTouchAreaSize() {
        mTouchSize = if (mTouchMode == TOUCH_MODE_BEZEL) {
            touchBezelSize
        } else if (mTouchMode == TOUCH_MODE_FULLSCREEN) {
            measuredWidth
        } else {
            0
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        check(childCount == 2) { "child count isn't equal to 2 , content and Menu view must be added in xml ." }
        val content = getChildAt(0)
        if (content != null) {
            removeView(content)
            mContentContainer!!.removeAllViews()
            mContentContainer!!.addView(
                content,
                LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            )
        } else {
            throw IllegalStateException(
                "content view must be added in xml ."
            )
        }
        val menu = getChildAt(0)
        if (menu != null) {
            removeView(menu)
            mMenuView = menu as FlowingMenuLayout
            mMenuView!!.setBackgroundColor(resources.getColor(android.R.color.transparent))
            mMenuView!!.setPaintColor(mMenuBackground)
            mMenuView!!.setMenuPosition(_position_)
            mMenuContainer!!.removeAllViews()
            mMenuContainer!!.addView(
                menu,
                LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            )
        } else {
            throw IllegalStateException(
                "menu view must be added in xml ."
            )
        }
        addView(
            mContentContainer,
            -1,
            LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        )
        addView(
            mMenuContainer,
            -1,
            LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        )
    }

    /**
     * Called when the number of pixels the content should be offset by has changed.
     *
     * @param offsetPixels The number of pixels to offset the content by.
     */
    protected abstract fun onOffsetPixelsChanged(offsetPixels: Int)
    /**
     * Toggles the menu open and close.
     *
     * @param animate Whether open/close should be animated.
     */
    /**
     * Toggles the menu open and close with animation.
     */
    @JvmOverloads
    fun toggleMenu(animate: Boolean = true): Boolean {
        return if (mDrawerState == STATE_OPEN || mDrawerState == STATE_OPENING) {
            closeMenu(animate)
            false
        } else if (mDrawerState == STATE_CLOSED || mDrawerState == STATE_CLOSING) {
            openMenu(animate)
            true
        } else {
            false
        }
    }

    /**
     * Animates the menu open.
     */
    fun openMenu() {
        openMenu(true)
    }

    /**
     * Opens the menu.
     *
     * @param animate Whether open/close should be animated.
     */
    abstract fun openMenu(animate: Boolean)
    abstract fun openMenu(animate: Boolean, y: Float)

    /**
     * Animates the menu closed.
     */
    fun closeMenu() {
        closeMenu(true)
    }

    /**
     * Closes the menu.
     *
     * @param animate Whether open/close should be animated.
     */
    abstract fun closeMenu(animate: Boolean)
    abstract fun closeMenu(animate: Boolean, y: Float)

    /**
     * Set the size of the menu drawer when open.
     *
     * @param size The size of the menu.
     */
    fun setMenuSize(size: Int) {
        mMenuSize = size
        if (mDrawerState == STATE_OPEN || mDrawerState == STATE_OPENING) {
            setOffsetPixels(mMenuSize.toFloat(), 0f, FlowingMenuLayout.TYPE_NONE)
        }
        requestLayout()
        invalidate()
    }

    protected fun smoothClose(eventY: Int) {
        try {
            endDrag()
            drawerState = STATE_CLOSING
            val valueAnimator: ValueAnimator = ValueAnimator.ofFloat(mOffsetPixels, 0f)
            valueAnimator.addUpdateListener { animation ->
                setOffsetPixels(
                    animation.animatedValue as Float, eventY.toFloat(),
                    FlowingMenuLayout.TYPE_DOWN_SMOOTH
                )
            }
            valueAnimator.addListener(object : FlowingAnimationListener() {
                override fun onAnimationEnd(animation: Animator?) {
                    mMenuVisible = false
                    setOffsetPixels(0f, 0f, FlowingMenuLayout.TYPE_NONE)
                    drawerState = STATE_CLOSED
                    stopLayerTranslation()
                }
            })
            valueAnimator.duration = 500
            valueAnimator.interpolator = DecelerateInterpolator(4f)
            valueAnimator.start()
        } catch (e: NoClassDefFoundError) {
            Log.e(TAG, "smoothClose: " + e.message)
        }
    }

    /**
     * Moves the drawer to the position passed.
     *
     * @param position The position the content is moved to.
     * @param velocity Optional velocity if called by releasing a drag event.
     * @param animate  Whether the move is animated.
     */
    protected fun animateOffsetTo(position: Int, velocity: Int, animate: Boolean, eventY: Float) {
        var velocity = velocity
        endDrag()
        val startX = mOffsetPixels.toInt()
        val dx = position - startX
        if (dx == 0 || !animate) {
            setOffsetPixels(position.toFloat(), 0f, FlowingMenuLayout.TYPE_NONE)
            drawerState =
                if (position == 0) STATE_CLOSED else STATE_OPEN
            stopLayerTranslation()
            return
        }
        var duration: Int
        velocity = Math.abs(velocity)
        duration = if (velocity > 0) {
            4 * Math.round(1000f * Math.abs(dx.toFloat() / velocity))
        } else {
            (600f * Math.abs(dx.toFloat() / mMenuSize)).toInt()
        }
        duration = Math.min(duration, mMaxAnimationDuration)
        animateOffsetTo(position, duration, eventY)
    }

    protected fun animateOffsetTo(position: Int, duration: Int, eventY: Float) {
        val startX = mOffsetPixels.toInt()
        val dx = position - startX
        drawerState = if (position == Position.LEFT) {
            if (dx > 0) {
                STATE_OPENING
            } else {
                STATE_CLOSING
            }
        } else {
            if (dx > 0) {
                STATE_CLOSING
            } else {
                STATE_OPENING
            }
        }
        mScroller!!.startScroll(startX, 0, dx, 0, duration)
        this.eventY = eventY
        startLayerTranslation()
        postAnimationInvalidate()
    }

    /**
     * Sets the number of pixels the content should be offset.
     *
     * @param offsetPixels The number of pixels to offset the content by.
     */
    protected fun setOffsetPixels(offsetPixels: Float, eventY: Float, type: Int) {
        val oldOffset = mOffsetPixels.toInt()
        val newOffset = offsetPixels.toInt()
        mOffsetPixels = offsetPixels
        mMenuView!!.setClipOffsetPixels(mOffsetPixels, eventY, type)
        if (newOffset != oldOffset) {
            onOffsetPixelsChanged(newOffset)
            mMenuVisible = newOffset != 0

            // Notify any attached listeners of the current open ratio
            val openRatio = Math.abs(newOffset).toFloat() / mMenuSize
            dispatchOnDrawerSlide(openRatio, newOffset)
        }
    }

    override fun onRtlPropertiesChanged(layoutDirection: Int) {
        super.onRtlPropertiesChanged(layoutDirection)
        if (_position_ != mResolvedPosition) {
            mResolvedPosition = _position_
            setOffsetPixels(mOffsetPixels * -1, 0f, FlowingMenuLayout.TYPE_NONE)
        }
        requestLayout()
        invalidate()
    }

    protected var _position_: Int
        protected get() {
            val layoutDirection: Int = ViewHelper.getLayoutDirection(this)
            when (mPosition) {
                Position.START -> return if (layoutDirection == LAYOUT_DIRECTION_RTL) {
                    Position.RIGHT
                } else {
                    Position.LEFT
                }
                Position.END -> return if (layoutDirection == LAYOUT_DIRECTION_RTL) {
                    Position.LEFT
                } else {
                    Position.RIGHT
                }
            }
            return mPosition
        }
        private set(position) {
            mPosition = position
            mResolvedPosition = position
        }

    /**
     * Register a callback to be invoked when the drawer state changes.
     *
     * @param listener The callback that will run.
     */
    fun setOnDrawerStateChangeListener(listener: OnDrawerStateChangeListener?) {
        mOnDrawerStateChangeListener = listener
    }

    /**
     * Register a callback that will be invoked when the drawer is about to intercept touch events.
     *
     * @param listener The callback that will be invoked.
     */
    fun setOnInterceptMoveEventListener(listener: OnInterceptMoveEventListener?) {
        mOnInterceptMoveEventListener = listener
    }

    /**
     * Sets the maximum duration of open/close animations.
     *
     * @param duration The maximum duration in milliseconds.
     */
    fun setMaxAnimationDuration(duration: Int) {
        mMaxAnimationDuration = duration
    }

    val menuContainer: ViewGroup?
        get() = mMenuContainer

    /**
     * Returns the ViewGroup used as a parent for the content view.
     *
     * @return The content view's parent.
     */
    val contentContainer: ViewGroup?
        get() = mContentContainer

    /**
     * Get the current state of the drawer.
     *
     * @return The state of the drawer.
     */
    var drawerState: Int
        get() = mDrawerState
        protected set(state) {
            if (state != mDrawerState) {
                val oldState = mDrawerState
                mDrawerState = state
                if (mOnDrawerStateChangeListener != null) {
                    mOnDrawerStateChangeListener!!.onDrawerStateChange(oldState, state)
                }
                if (DEBUG) {
                    logDrawerState(state)
                }
            }
        }

    protected fun logDrawerState(state: Int) {
        when (state) {
            STATE_CLOSED -> Log.d(TAG, "[DrawerState] STATE_CLOSED")
            STATE_CLOSING -> Log.d(TAG, "[DrawerState] STATE_CLOSING")
            STATE_DRAGGING_CLOSE -> Log.d(TAG, "[DrawerState] STATE_DRAGGING_CLOSE")
            STATE_DRAGGING_OPEN -> Log.d(TAG, "[DrawerState] STATE_DRAGGING_OPEN")
            STATE_OPENING -> Log.d(TAG, "[DrawerState] STATE_OPENING")
            STATE_OPEN -> Log.d(TAG, "[DrawerState] STATE_OPEN")
            else -> Log.d(TAG, "[DrawerState] Unknown: $state")
        }
    }

    /**
     * Sets the drawer touch mode. Possible values are [.TOUCH_MODE_NONE], [.TOUCH_MODE_BEZEL] or
     * [.TOUCH_MODE_FULLSCREEN].
     *
     * @param mode The touch mode.
     */
    fun setTouchMode(mode: Int) {
        if (mTouchMode != mode) {
            mTouchMode = mode
            updateTouchAreaSize()
        }
    }

    override fun postOnAnimation(action: Runnable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            super.postOnAnimation(action)
        } else {
            postDelayed(action, ANIMATION_DELAY.toLong())
        }
    }

    private fun dispatchOnDrawerSlide(openRatio: Float, offsetPixels: Int) {
        if (mOnDrawerStateChangeListener != null) {
            mOnDrawerStateChangeListener!!.onDrawerSlide(openRatio, offsetPixels)
        }
    }

    override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)
    }

    fun saveState(state: Bundle) {
        val menuVisible = mDrawerState == STATE_OPEN || mDrawerState == STATE_OPENING
        state.putBoolean(STATE_MENU_VISIBLE, menuVisible)
    }

    /**
     * Restores the state of the drawer.
     *
     * @param in A parcelable containing the drawer state.
     */
    fun restoreState(`in`: Parcelable?) {
        mState = `in` as Bundle?
        val menuOpen = mState!!.getBoolean(STATE_MENU_VISIBLE)
        if (menuOpen) {
            openMenu(false)
        } else {
            setOffsetPixels(0f, 0f, FlowingMenuLayout.TYPE_NONE)
        }
        mDrawerState = if (menuOpen) STATE_OPEN else STATE_CLOSED
    }

    override fun onSaveInstanceState(): Parcelable? {
        val superState = super.onSaveInstanceState()
        val state = SavedState(superState)
        if (mState == null) {
            mState = Bundle()
        }
        saveState(mState!!)
        state.mState = mState
        return state
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        val savedState = state as SavedState
        super.onRestoreInstanceState(savedState.superState)
        restoreState(savedState.mState)
    }

    internal class SavedState : BaseSavedState {
        var mState: Bundle? = null

        constructor(superState: Parcelable?) : super(superState) {}

        @SuppressLint("ParcelClassLoader")
        constructor(`in`: Parcel) : super(`in`) {
            mState = `in`.readBundle()
        }

        override fun writeToParcel(dest: Parcel, flags: Int) {
            super.writeToParcel(dest, flags)
            dest.writeBundle(mState)
        }

        companion object {
            @JvmField
            val CREATOR: Parcelable.Creator<SavedState?> =
                object : Parcelable.Creator<SavedState?> {
                    override fun createFromParcel(p: Parcel?): SavedState? {
                        return p?.let { SavedState(it) }
                    }

                    override fun newArray(size: Int): Array<SavedState?> {
                        return arrayOfNulls(size)
                    }

                }
        }
    }

    protected fun getXVelocity(velocityTracker: VelocityTracker): Float {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            velocityTracker.getXVelocity(mActivePointerId)
        } else velocityTracker.xVelocity
    }

    protected fun canChildrenScroll(dx: Int, x: Int, y: Int): Boolean {
        var canScroll = false
        when (_position_) {
            Position.LEFT, Position.RIGHT -> canScroll = if (!mMenuVisible) {
                canChildScrollHorizontally(
                    mContentContainer,
                    false,
                    dx,
                    x - ViewHelper.getLeft(mContentContainer!!),
                    y - ViewHelper.getTop(mContentContainer!!)
                )
            } else {
                canChildScrollHorizontally(
                    mMenuContainer,
                    false,
                    dx,
                    x - ViewHelper.getLeft(mMenuContainer!!),
                    y - ViewHelper.getTop(mContentContainer!!)
                )
            }
        }
        return canScroll
    }

    /**
     * Tests scrollability within child views of v given a delta of dx.
     *
     * @param v      View to test for horizontal scrollability
     * @param checkV Whether the view should be checked for draggability
     * @param dx     Delta scrolled in pixels
     * @param x      X coordinate of the active touch point
     * @param y      Y coordinate of the active touch point
     * @return true if child views of v can be scrolled by delta of dx.
     */
    protected fun canChildScrollHorizontally(
        v: View?,
        checkV: Boolean,
        dx: Int,
        x: Int,
        y: Int
    ): Boolean {
        if (v is ViewGroup) {
            val group = v
            val count = group.childCount
            // Count backwards - let topmost views consume scroll distance first.
            for (i in count - 1 downTo 0) {
                val child = group.getChildAt(i)
                val childLeft = child.left + supportGetTranslationX(child)
                val childRight = child.right + supportGetTranslationX(child)
                val childTop = child.top + supportGetTranslationY(child)
                val childBottom = child.bottom + supportGetTranslationY(child)
                if (x >= childLeft && x < childRight && y >= childTop && y < childBottom && canChildScrollHorizontally(
                        child,
                        true,
                        dx,
                        x - childLeft,
                        y - childTop
                    )
                ) {
                    return true
                }
            }
        }
        return checkV && mOnInterceptMoveEventListener!!.isViewDraggable(v, dx, x, y)
    }

    private fun supportGetTranslationY(v: View): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            v.translationY.toInt()
        } else 0
    }

    private fun supportGetTranslationX(v: View): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            v.translationX.toInt()
        } else 0
    }

    protected val isCloseEnough: Boolean
        protected get() = Math.abs(mOffsetPixels) <= mCloseEnough

    /**
     * Callback when each frame in the drawer animation should be drawn.
     */
    private fun postAnimationInvalidate() {
        if (mScroller!!.computeScrollOffset()) {
            val oldX = mOffsetPixels.toInt()
            val x = mScroller!!.currX
            if (x != oldX) {
                if (mDrawerState == STATE_OPENING) {
                    setOffsetPixels(x.toFloat(), eventY, FlowingMenuLayout.TYPE_UP_AUTO)
                } else if (mDrawerState == STATE_CLOSING) {
                    setOffsetPixels(x.toFloat(), eventY, FlowingMenuLayout.TYPE_DOWN_AUTO)
                }
            }
            if (x != mScroller!!.finalX) {
                postOnAnimation(mDragRunnable)
                return
            }
        }
        if (mDrawerState == STATE_OPENING) {
            completeAnimation()
        } else if (mDrawerState == STATE_CLOSING) {
            mScroller!!.abortAnimation()
            val finalX = mScroller!!.finalX
            mMenuVisible = finalX != 0
            setOffsetPixels(finalX.toFloat(), 0f, FlowingMenuLayout.TYPE_NONE)
            drawerState =
                if (finalX == 0) STATE_CLOSED else STATE_OPEN
            stopLayerTranslation()
        }
    }

    /**
     * Called when a drawer animation has successfully completed.
     */
    private fun completeAnimation() {
        mScroller!!.abortAnimation()
        val finalX = mScroller!!.finalX
        flowDown(finalX)
    }

    private fun flowDown(finalX: Int) {
        try {
            val valueAnimator: ValueAnimator = ValueAnimator.ofFloat(0f, 1f)
            valueAnimator.addUpdateListener { animation -> mMenuView!!.setUpDownFraction(animation.animatedFraction) }
            valueAnimator.addListener(object : FlowingAnimationListener() {
                override fun onAnimationEnd(animation: Animator?) {
                    if (mDrawerState == STATE_OPENING) {
                        mMenuVisible = finalX != 0
                        setOffsetPixels(finalX.toFloat(), 0f, FlowingMenuLayout.TYPE_NONE)
                        drawerState =
                            if (finalX == 0) STATE_CLOSED else STATE_OPEN
                        stopLayerTranslation()
                    }
                }
            })
            valueAnimator.duration = 300
            valueAnimator.interpolator = OvershootInterpolator(4f)
            valueAnimator.start()
        } catch (e: NoClassDefFoundError) {
        }
    }

    /**
     * Called when a drag has been ended.
     */
    protected fun endDrag() {
        mIsDragging = false
        if (mVelocityTracker != null) {
            mVelocityTracker!!.recycle()
            mVelocityTracker = null
        }
    }

    /**
     * Stops ongoing animation of the drawer.
     */
    protected open fun stopAnimation() {
        removeCallbacks(mDragRunnable)
        mScroller!!.abortAnimation()
        stopLayerTranslation()
    }

    /**
     * If possible, set the layer type to [View.LAYER_TYPE_HARDWARE].
     */
    @SuppressLint("NewApi")
    protected open fun startLayerTranslation() {
        if (mHardwareLayersEnabled && !mLayerTypeHardware) {
            mLayerTypeHardware = true
            mContentContainer!!.setLayerType(LAYER_TYPE_HARDWARE, null)
            mMenuContainer!!.setLayerType(LAYER_TYPE_HARDWARE, null)
        }
    }

    /**
     * If the current layer type is [View.LAYER_TYPE_HARDWARE], this will set it to
     * [View.LAYER_TYPE_NONE].
     */
    @SuppressLint("NewApi")
    protected open fun stopLayerTranslation() {
        if (mLayerTypeHardware) {
            mLayerTypeHardware = false
            mContentContainer!!.setLayerType(LAYER_TYPE_NONE, null)
            mMenuContainer!!.setLayerType(LAYER_TYPE_NONE, null)
        }
    }

    fun setHardwareLayerEnabled(enabled: Boolean) {
        if (enabled != mHardwareLayersEnabled) {
            mHardwareLayersEnabled = enabled
            mMenuContainer!!.setHardwareLayersEnabled(enabled)
            mContentContainer!!.setHardwareLayersEnabled(enabled)
            stopLayerTranslation()
        }
    }

    companion object {
        /**
         * Tag used when logging.
         */
        private const val TAG = "ElasticDrawer"

        /**
         * Indicates whether debug code should be enabled.
         */
        private const val DEBUG = false

        /**
         * The time between each frame when animating the drawer.
         */
        protected const val ANIMATION_DELAY = 1000 / 60

        /**
         * Interpolator used when animating the drawer open/closed.
         */
        protected val SMOOTH_INTERPOLATOR: Interpolator = SmoothInterpolator()

        /**
         * The default touch bezel size of the drawer in dp.
         */
        private const val DEFAULT_DRAG_BEZEL_DP = 32

        /**
         * Distance in dp from closed position from where the drawer is considered closed with regards to touch events.
         */
        private const val CLOSE_ENOUGH = 3

        /**
         * Disallow opening the drawer by dragging the screen.
         */
        const val TOUCH_MODE_NONE = 0

        /**
         * Allow opening drawer only by dragging on the edge of the screen.
         */
        const val TOUCH_MODE_BEZEL = 1

        /**
         * Allow opening drawer by dragging anywhere on the screen.
         */
        const val TOUCH_MODE_FULLSCREEN = 2

        /**
         * The maximum animation duration.
         */
        private const val DEFAULT_ANIMATION_DURATION = 600

        /**
         * Indicates that the drawer is currently closed.
         */
        const val STATE_CLOSED = 0

        /**
         * Indicates that the drawer is currently closing.
         */
        const val STATE_CLOSING = 1

        /**
         * Indicates that the drawer is currently being dragged by the user.
         */
        const val STATE_DRAGGING_OPEN = 2

        /**
         * Indicates that the drawer is currently being dragged by the user.
         */
        const val STATE_DRAGGING_CLOSE = 4

        /**
         * Indicates that the drawer is currently opening.
         */
        const val STATE_OPENING = 6

        /**
         * Indicates that the drawer is currently open.
         */
        const val STATE_OPEN = 8

        /**
         * Key used when saving menu visibility state.
         */
        private const val STATE_MENU_VISIBLE = "ElasticDrawer.menuVisible"
        const val INVALID_POINTER = -1
    }
}
