package com.doubleclick.doctorapp.android.views.videoView.core.listener

import androidx.media3.common.VideoSize

interface VideoSizeListener {
  fun onVideoSizeChanged(videoSize: VideoSize)
}