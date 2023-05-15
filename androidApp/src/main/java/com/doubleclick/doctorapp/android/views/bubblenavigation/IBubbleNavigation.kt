package com.doubleclick.doctorapp.android.views.bubblenavigation

import android.graphics.Typeface
import com.doubleclick.doctorapp.android.views.bubblenavigation.listener.BubbleNavigationChangeListener

interface IBubbleNavigation {

    fun setNavigationChangeListener(navigationChangeListener: BubbleNavigationChangeListener)

    fun setTypeface(typeface: Typeface)

    fun getCurrentActiveItemPosition(): Int

    fun setCurrentActiveItem(position: Int)

    fun setBadgeValue(position: Int, value: String)
}