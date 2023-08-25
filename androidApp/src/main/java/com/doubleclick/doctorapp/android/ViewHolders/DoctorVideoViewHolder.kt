package com.doubleclick.doctorapp.android.ViewHolders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.doctorapp.android.R
import org.w3c.dom.Text

open class DoctorVideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val video_view_doctor: ImageView = itemView.findViewById(R.id.video_view_doctor);

}