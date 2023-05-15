package com.doubleclick.doctorapp.android.views.bubblenavigation

import android.graphics.Color
import android.graphics.drawable.Drawable

class BubbleToggleItem {
    private var icon: Drawable? = null
    private var shape: Drawable? = null
    private var title = ""

    private var colorActive = Color.BLUE
    private var colorInactive = Color.BLACK
    private var shapeColor = Int.MIN_VALUE

    private var badgeText: String? = null
    private var badgeTextColor = Color.WHITE
    private var badgeBackgroundColor = Color.BLACK

    private var titleSize = 0f
    private var badgeTextSize = 0f
    private var iconWidth = 0f
    private var iconHeight: Float = 0f

    private var titlePadding = 0
    private var internalPadding = 0

    constructor()

    fun getIcon(): Drawable? {
        return icon
    }

    fun setIcon(icon: Drawable?) {
        this.icon = icon
    }

    fun getShape(): Drawable? {
        return shape
    }

    fun setShape(shape: Drawable?) {
        this.shape = shape
    }

    fun getShapeColor(): Int {
        return shapeColor
    }

    fun setShapeColor(shapeColor: Int) {
        this.shapeColor = shapeColor
    }

    fun getTitle(): String? {
        return title
    }

    fun setTitle(title: String) {
        this.title = title
    }

    fun getColorActive(): Int {
        return colorActive
    }

    fun setColorActive(colorActive: Int) {
        this.colorActive = colorActive
    }

    fun getColorInactive(): Int {
        return colorInactive
    }

    fun setColorInactive(colorInactive: Int) {
        this.colorInactive = colorInactive
    }

    fun getTitleSize(): Float {
        return titleSize
    }

    fun setTitleSize(titleSize: Float) {
        this.titleSize = titleSize
    }

    fun getIconWidth(): Float {
        return iconWidth
    }

    fun setIconWidth(iconWidth: Float) {
        this.iconWidth = iconWidth
    }

    fun getIconHeight(): Float {
        return iconHeight
    }

    fun setIconHeight(iconHeight: Float) {
        this.iconHeight = iconHeight
    }

    fun getTitlePadding(): Int {
        return titlePadding
    }

    fun setTitlePadding(titlePadding: Int) {
        this.titlePadding = titlePadding
    }

    fun getInternalPadding(): Int {
        return internalPadding
    }

    fun setInternalPadding(internalPadding: Int) {
        this.internalPadding = internalPadding
    }

    fun getBadgeTextColor(): Int {
        return badgeTextColor
    }

    fun setBadgeTextColor(badgeTextColor: Int) {
        this.badgeTextColor = badgeTextColor
    }

    fun getBadgeBackgroundColor(): Int {
        return badgeBackgroundColor
    }

    fun setBadgeBackgroundColor(badgeBackgroundColor: Int) {
        this.badgeBackgroundColor = badgeBackgroundColor
    }

    fun getBadgeTextSize(): Float {
        return badgeTextSize
    }

    fun setBadgeTextSize(badgeTextSize: Float) {
        this.badgeTextSize = badgeTextSize
    }

    fun getBadgeText(): String? {
        return badgeText
    }

    fun setBadgeText(badgeText: String?) {
        this.badgeText = badgeText
    }
}
