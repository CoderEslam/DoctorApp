package com.doubleclick.doctorapp.android.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.bumptech.glide.Glide
import com.doubleclick.doctorapp.android.R
import com.giphy.sdk.core.models.enums.RatingType

class StackViewAdapter(
    ctx: Context,
    var imagesList: List<String>
) : ArrayAdapter<String>(ctx, 0) {


    // on below line creating a get count method.
    override fun getCount(): Int {
        return imagesList.size
    }

    // on below line creating a get view method to inflate the layout file.
    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        // on below line checking if the view is empty or not.
        var convertView: View? = convertView
        if (convertView == null) {
            // if the view is empty we are inflating the layout file on below line.
            // on below line specifying the layout file name which we have already created.
            convertView =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.stack_item_image, parent, false)
        }
        val imageView: ImageView = convertView!!.findViewById(R.id.image)
        Glide.with(convertView.context).load(imagesList[position]).into(imageView)
        return convertView!!
    }
}