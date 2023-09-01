package com.doubleclick.doctorapp.android.ViewHolders

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.doctorapp.android.R
import com.doubleclick.doctorapp.android.views.videoView.ui.widget.VideoView
import com.google.android.material.progressindicator.LinearProgressIndicator

open class DoctorVideoViewPagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    //    val video_view_doctor: ImageView = itemView.findViewById(R.id.video_view_doctor);
    val video_: VideoView = itemView.findViewById(R.id.video_);
    val image_doctor: ImageView = itemView.findViewById(R.id.image_doctor);
    val name_doctor: TextView = itemView.findViewById(R.id.name_doctor);
    val progress: ProgressBar = itemView.findViewById(R.id.progress);

}