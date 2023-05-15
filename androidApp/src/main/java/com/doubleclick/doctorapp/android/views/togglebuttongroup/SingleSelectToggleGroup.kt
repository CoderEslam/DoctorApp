package com.doubleclick.doctorapp.android.views.togglebuttongroup

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.Checkable
import com.doubleclick.doctorapp.android.views.togglebuttongroup.button.MarkerButton

/**
 * Created By Eslam Ghazy on 11/20/2022
 */

class SingleSelectToggleGroup : ToggleButtonGroup {
    protected var mOnCheckedChangeListener: OnCheckedChangeListener? = null
    private var mCheckedId = View.NO_ID

    constructor(context: Context?) : super(context!!) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {}

    protected override fun onFinishInflate() {
        super.onFinishInflate()
        val initialCheckedId: Int =
            if (mInitialCheckedId !== View.NO_ID) mInitialCheckedId else mSilentInitialCheckedId
        if (initialCheckedId != View.NO_ID) {
            setCheckedStateForView(initialCheckedId, true)
            setCheckedId(initialCheckedId, false)
        }
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child is Checkable) {
            val checkable = child as Checkable
            if (checkable.isChecked) {
                if (mCheckedId != -1) {
                    setCheckedStateForView(mCheckedId, false)
                }
                if (child.id == View.NO_ID) {
                    child.id = generateIdForView(child)
                }
                setCheckedId(child.id)
            }
            if (child is MarkerButton) {
                (child as MarkerButton).setRadioStyle(true)
            }
        }
        super.addView(child, index, params)
    }

    protected override fun <T> onChildCheckedChange(
        child: T,
        isChecked: Boolean
    ) where T : View?, T : Checkable? {
        if (isChecked) {
            if (mCheckedId != View.NO_ID && mCheckedId != child!!.id) {
                setCheckedStateForView(mCheckedId, false)
            }
            val id = child!!.id
            if (mSilentInitialCheckedId === id) {
                mSilentInitialCheckedId = View.NO_ID
                setCheckedId(id, false)
            } else {
                setCheckedId(id)
            }
        }
    }

    fun check(id: Int) {
        if (id == mCheckedId) {
            return
        }
        if (mCheckedId != -1) {
            setCheckedStateForView(mCheckedId, false)
        }
        setCheckedStateForView(id, true)
        setCheckedId(id, false)
    }

    fun getCheckedId(): Int {
        return mCheckedId
    }

    fun clearCheck() {
        check(-1)
    }

    fun setOnCheckedChangeListener(listener: OnCheckedChangeListener?) {
        mOnCheckedChangeListener = listener
    }

    private fun setCheckedId(id: Int) {
        setCheckedId(id, true)
    }

    private fun setCheckedId(id: Int, notify: Boolean) {
        mCheckedId = id
        if (notify) {
            notifyCheckedChange(mCheckedId)
        }
    }

    private fun notifyCheckedChange(id: Int) {
        if (mOnCheckedChangeListener != null) {
            mOnCheckedChangeListener!!.onCheckedChanged(this, id)
        }
    }

    interface OnCheckedChangeListener {
        fun onCheckedChanged(group: SingleSelectToggleGroup?, checkedId: Int)
    }

    companion object {
        private val LOG_TAG = SingleSelectToggleGroup::class.java.simpleName
    }
}
