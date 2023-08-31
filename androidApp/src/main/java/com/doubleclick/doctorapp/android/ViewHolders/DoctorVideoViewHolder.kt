package com.doubleclick.doctorapp.android.ViewHolders

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.doctorapp.android.R
import com.doubleclick.doctorapp.android.views.videoView.ui.widget.VideoView

open class DoctorVideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val video_view_doctor: VideoView = itemView.findViewById(R.id.video_view_doctor);
    val image: ImageView = itemView.findViewById(R.id.image);

}