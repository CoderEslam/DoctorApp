package com.doubleclick.doctorapp.android.views.videoView.nmp.manager.window

import androidx.media3.common.Timeline

data class WindowInfo(
  val previousWindowIndex: Int,
  val currentWindowIndex: Int,
  val nextWindowIndex: Int,
  val currentWindow: Timeline.Window
)