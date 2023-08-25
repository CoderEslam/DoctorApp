package com.doubleclick.doctorapp.android.views.videoView.core.listener

import com.doubleclick.doctorapp.android.views.videoView.listener.OnTimelineChangedListener
import com.doubleclick.doctorapp.android.views.videoView.core.state.PlaybackStateListener
import com.doubleclick.doctorapp.android.views.videoView.listener.OnSeekCompletionListener
import com.doubleclick.doctorapp.android.views.videoView.nmp.ExoMediaPlayer

/**
 * A listener for core [ExoMediaPlayer] events
 */
interface ExoPlayerListener : OnSeekCompletionListener, PlaybackStateListener,
    OnTimelineChangedListener {
  fun onError(player: ExoMediaPlayer, e: Exception?)

  fun onVideoSizeChanged(width: Int, height: Int, unAppliedRotationDegrees: Int, pixelWidthHeightRatio: Float)
}
