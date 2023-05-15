package com.doubleclick.doctorapp.android.views.SeekArc.`interface`

import com.doubleclick.doctorapp.android.views.SeekArc.SeekArc

interface OnSeekArcChangeListener {
    /**
     * Notification that the progress level has changed. Clients can use the
     * fromUser parameter to distinguish user-initiated changes from those
     * that occurred programmatically.
     *
     * @param seekArc
     * The SeekArc whose progress has changed
     * @param progress
     * The current progress level. This will be in the range
     * 0..max where max was set by
     * [ProgressArc.setMax]. (The default value for
     * max is 100.)
     * @param fromUser
     * True if the progress change was initiated by the user.
     */
    fun onProgressChanged(seekArc: SeekArc?, progress: Int, fromUser: Boolean)

    /**
     * Notification that the user has started a touch gesture. Clients may
     * want to use this to disable advancing the seekbar.
     *
     * @param seekArc
     * The SeekArc in which the touch gesture began
     */
    fun onStartTrackingTouch(seekArc: SeekArc?)

    /**
     * Notification that the user has finished a touch gesture. Clients may
     * want to use this to re-enable advancing the seekarc.
     *
     * @param seekArc
     * The SeekArc in which the touch gesture began
     */
    fun onStopTrackingTouch(seekArc: SeekArc?)
}