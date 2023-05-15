package com.doubleclick.doctorapp.android.Home.menu

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.doubleclick.doctorapp.android.R

class SimpleItem(private val icon: Drawable, private val title: String) :
    DrawerItem<SimpleItem.ViewHolder?>() {
    private var selectedItemIconTint = 0
    private var selectedItemTextTint = 0
    private var normalItemIconTint = 0
    private var normalItemTextTint = 0


    fun withSelectedIconTint(selectedItemIconTint: Int): SimpleItem {
        this.selectedItemIconTint = selectedItemIconTint
        return this
    }

    fun withSelectedTextTint(selectedItemTextTint: Int): SimpleItem {
        this.selectedItemTextTint = selectedItemTextTint
        return this
    }

    fun withIconTint(normalItemIconTint: Int): SimpleItem {
        this.normalItemIconTint = normalItemIconTint
        return this
    }

    fun withTextTint(normalItemTextTint: Int): SimpleItem {
        this.normalItemTextTint = normalItemTextTint
        return this
    }

    class ViewHolder(itemView: View) : DrawerAdapter.ViewHolder(itemView) {
        val icon: ImageView = itemView.findViewById(R.id.icon)
        val name: TextView = itemView.findViewById(R.id.name)
        val root_linear_layout: LinearLayout = itemView.findViewById(R.id.root_linear_layout)
    }

    override fun createViewHolder(parent: ViewGroup?): ViewHolder? {
        val inflater = LayoutInflater.from(parent!!.context)
        val v: View = inflater.inflate(R.layout.item_option, parent, false)
        return ViewHolder(v)
    }

    override fun bindViewHolder(holder: ViewHolder?) {
        holder!!.name.text = title
        holder!!.icon.setImageDrawable(icon)
//        holder!!.title.setTextColor(if (isChecked) selectedItemTextTint else normalItemTextTint)
//        holder!!.icon.setColorFilter(if (isChecked) selectedItemIconTint else normalItemIconTint)
        /*holder!!.root_linear_layout.background = if (isChecked) ContextCompat.getDrawable(
            holder.itemView.context,
            R.drawable.bg_alpha_white
        ) else ContextCompat.getDrawable(
            holder.itemView.context,
            R.drawable.bg_color_less
        )*/
    }
}
