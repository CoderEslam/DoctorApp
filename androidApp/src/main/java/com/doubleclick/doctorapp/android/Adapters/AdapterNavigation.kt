package com.doubleclick.doctorapp.android.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.doctorapp.android.ItemNavigationListener
import com.doubleclick.doctorapp.android.Model.ItemNavigation
import com.doubleclick.doctorapp.android.R
import com.doubleclick.doctorapp.android.ViewHolders.NavigationViewHolder

class AdapterNavigation(
    val itemNavigationListener: ItemNavigationListener,
    val items: List<ItemNavigation>
) :
    RecyclerView.Adapter<NavigationViewHolder>() {

    private var lastCheckedPosition: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavigationViewHolder {
        return NavigationViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_option, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NavigationViewHolder, position: Int) {
        holder.name.text =
            holder.itemView.resources.getString(items[holder.absoluteAdapterPosition].name)
        holder.icon.setImageDrawable(
            ContextCompat.getDrawable(
                holder.itemView.context,
                items[holder.absoluteAdapterPosition].icon
            )
        )

        /*if (items[holder.absoluteAdapterPosition].index == lastCheckedPosition) {
            holder.name.setTextColor(
                holder.itemView.context.resources.getColor(
                    R.color.grey_text
                )
            )
            holder.root_linear_layout.background =
                ContextCompat.getDrawable(
                    holder.itemView.context,
                    R.drawable.bg_alpha_white
                )

        } else {
            holder.root_linear_layout.background =
                ContextCompat.getDrawable(
                    holder.itemView.context,
                    R.color.color_less
                )
            holder.name.setTextColor(
                holder.itemView.context.resources.getColor(
                    R.color.black
                )
            )
        }*/
        holder.itemView.setOnClickListener {
            itemNavigationListener.itemNavigation(items[position].index)
            lastCheckedPosition = items[position].index
            notifyItemRangeChanged(0, items.size)
        }
    }

    override fun getItemCount(): Int = items.size
}