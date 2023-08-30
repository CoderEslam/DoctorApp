package com.doubleclick.doctorapp.android.ViewHolders

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.doctorapp.android.R
import com.doubleclick.doctorapp.android.views.videoView.ui.widget.VideoView

open class DoctorVideoViewPagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val video_view_doctor: ImageView = itemView.findViewById(R.id.video_view_doctor);
    val video_: VideoView = itemView.findViewById(R.id.video_);

}