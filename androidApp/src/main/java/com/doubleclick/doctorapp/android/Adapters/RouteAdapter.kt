package com.doubleclick.doctorapp.android.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.doctorapp.android.Model.RouteModel
import com.doubleclick.doctorapp.android.OnRoute
import com.doubleclick.doctorapp.android.R
import com.doubleclick.doctorapp.android.ViewHolders.RouteViewModel


class RouteAdapter(val routs: List<RouteModel>, val onRoute: OnRoute) :
    RecyclerView.Adapter<RouteViewModel>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteViewModel =
        RouteViewModel(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_item_route, parent, false)
        )


    override fun onBindViewHolder(holder: RouteViewModel, position: Int) {
        if (holder.bindingAdapterPosition % 2 === 1) {
            val layoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )
            layoutParams.setMargins(0, 150, 0, 0)
            holder.constrain_parent.layoutParams = layoutParams
        }
        holder.name.text = routs[holder.bindingAdapterPosition].name
        holder.itemView.setOnClickListener {
            onRoute.onClick(routs[holder.bindingAdapterPosition].tag)
        }
    }

    override fun getItemCount(): Int =
        routs.size

}