package com.doubleclick.doctorapp.android.ViewHolders

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.StackView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.doctorapp.android.R
import com.doubleclick.doctorapp.android.views.CircleImageView

class DoctorReservationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    init {
        load()
    }

    lateinit var smokingList: List<String>
    lateinit var alcoholDrinkingList: List<String>
    lateinit var bloodTypeList: List<String>
    lateinit var materielStatusList: List<String>
    val patent_name: TextView = itemView.findViewById(R.id.patent_name)
    val patent_phone: TextView = itemView.findViewById(R.id.patent_phone)
    val patent_age: TextView = itemView.findViewById(R.id.patent_age)
    val patent_reservation_time: TextView = itemView.findViewById(R.id.patent_reservation_time)
    val patent_blood: TextView = itemView.findViewById(R.id.patent_blood)
    val patent_smoking: TextView = itemView.findViewById(R.id.patent_smoking)
    val patent_marital_status: TextView = itemView.findViewById(R.id.patent_marital_status)
    val patent_drinking_alcohol: TextView = itemView.findViewById(R.id.patent_drinking_alcohol)
    val patent_height: TextView = itemView.findViewById(R.id.patent_height)
    val patent_weight: TextView = itemView.findViewById(R.id.patent_weight)
    val more_info: LinearLayout = itemView.findViewById(R.id.more_info)
    val arrow_open_more: ImageView = itemView.findViewById(R.id.arrow_open_more)
    val stack_item_image: StackView = itemView.findViewById(R.id.stack_view_image)

    fun load() {
        smokingList = itemView.context.resources.getStringArray(R.array.smokingList).toList()
        alcoholDrinkingList =
            itemView.context.resources.getStringArray(R.array.alcoholDrinkingList).toList()
        bloodTypeList =
            itemView.context.resources.getStringArray(R.array.bloodTypeList).toList()
        materielStatusList =
            itemView.context.resources.getStringArray(R.array.materielStatusList).toList()

    }

}