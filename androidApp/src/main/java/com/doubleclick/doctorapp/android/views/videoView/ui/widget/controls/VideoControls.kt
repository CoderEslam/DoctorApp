package com.doubleclick.doctorapp.android.views.videoView.ui.widget.controls

import com.doubleclick.doctorapp.android.views.videoView.ui.widget.VideoView
import com.doubleclick.doctorapp.android.views.videoView.core.state.PlaybackStateListener

/**
 * An Interface that represents the core VideoControl functionality that
 * the [VideoView] uses to inform the controls of updated states, etc.
 */
interface VideoControls: PlaybackStateListener {

  /**
   * Called when the controls have been registered by the
   * [VideoView].
   *
   * @param videoView The [VideoView] that the controls are attached to
   */
  fun onAttachedToView(videoView: VideoView)

  /**
   * Called when the controls have been cleaned up on the [VideoView]
   * side in preparation for detachment.
   *
   * @param videoView The [VideoView] that the controls are detaching from
   */
  fun onDetachedFromView(videoView: VideoView)
}