package com.doubleclick.doctorapp.android.views.SmoothButtom.ext

import android.content.Context
import kotlin.math.roundToInt

internal fun Context.d2p(dp: Float): Float {
    return (dp * resources.displayMetrics.density).roundToInt().toFloat()
}
