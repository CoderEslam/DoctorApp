package com.doubleclick.doctorapp.android.Home.menu

import android.view.ViewGroup

abstract class DrawerItem<out T : DrawerAdapter.ViewHolder?> {
    var isChecked = false
        protected set

    abstract fun createViewHolder(parent: ViewGroup?): T
    abstract fun bindViewHolder(holder: @UnsafeVariance T)
    fun setChecked(isChecked: Boolean): DrawerItem<T> {
        this.isChecked = isChecked
        return this
    }

    open val isSelectable: Boolean
        get() = true
}