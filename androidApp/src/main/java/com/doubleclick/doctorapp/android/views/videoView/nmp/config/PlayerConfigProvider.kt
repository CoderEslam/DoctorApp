package com.doubleclick.doctorapp.android.views.videoView.nmp.config

import android.content.Context
import com.doubleclick.doctorapp.android.views.videoView.nmp.config.PlayerConfig

/**
 * A simple provider for the [PlayerConfig]
 */
interface PlayerConfigProvider {
  /**
   * Provide the [PlayerConfig] for the requested context
   */
  fun getConfig(context: Context): PlayerConfig
}