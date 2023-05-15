package com.doubleclick.doctorapp.android.views.bubblenavigation.util

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.TypedValue
import com.doubleclick.doctorapp.android.R

object ViewUtils {
    fun getThemeAccentColor(context: Context): Int {
        val value = TypedValue()
        context.theme.resolveAttribute(R.color.green, value, true)
        return value.data
    }

    fun updateDrawableColor(drawable: Drawable?, color: Int) {
        if (drawable == null) return
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) drawable.setTint(color) else drawable.setColorFilter(
            color,
            PorterDuff.Mode.SRC_ATOP
        )
    }
}