package com.doubleclick.doctorapp.android.views.videoView.nmp.config

import android.content.Context

/**
 * The Default implementation for the [PlayerConfigProvider]
 */
class DefaultPlayerConfigProvider: PlayerConfigProvider {

  /**
   * Provides a [PlayerConfig] using the default values
   */
  override fun getConfig(context: Context): PlayerConfig {
    return PlayerConfigBuilder(context).build()
  }
}