package com.doubleclick.doctorapp.android.views.togglebuttongroup.button

import android.widget.Checkable

/**
 * Created By Eslam Ghazy on 11/20/2022
 */
open interface ToggleButton : Checkable {
    fun setOnCheckedChangeListener(listener: OnCheckedChangeListener?)
}
