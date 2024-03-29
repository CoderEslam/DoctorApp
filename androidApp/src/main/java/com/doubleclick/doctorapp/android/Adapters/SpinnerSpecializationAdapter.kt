package com.doubleclick.doctorapp.android.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.doubleclick.doctorapp.android.Model.Specialization.SpecializationList
import com.doubleclick.doctorapp.android.Model.Specialization.SpecializationModel
import com.doubleclick.doctorapp.android.R

class SpinnerSpecializationAdapter(
    val specialization: List<SpecializationModel>
) : BaseAdapter() {
    override fun getCount(): Int = specialization?.size ?: 0

    override fun getItem(i: Int): Any = i

    override fun getItemId(i: Int): Long = i.toLong()

    override fun getView(i: Int, p1: View?, viewGroup: ViewGroup?): View {
        val rootView: View =
            LayoutInflater.from(viewGroup?.context)
                .inflate(R.layout.spinner_layout, viewGroup, false)
        val text: TextView = rootView.findViewById(R.id.text_spinner);
        text.text = specialization?.get(i)?.name
        return rootView;
    }
}