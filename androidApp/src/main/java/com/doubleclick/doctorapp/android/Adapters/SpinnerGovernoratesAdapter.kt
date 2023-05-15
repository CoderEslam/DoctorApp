package com.doubleclick.doctorapp.android.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.doubleclick.doctorapp.android.Model.Governorates.GovernoratesModel
import com.doubleclick.doctorapp.android.R

class SpinnerGovernoratesAdapter(
    val list: List<GovernoratesModel>
) : BaseAdapter() {
    override fun getCount(): Int = list.size

    override fun getItem(i: Int): Any = i

    override fun getItemId(i: Int): Long = i.toLong()

    override fun getView(i: Int, p1: View?, viewGroup: ViewGroup?): View {
        val rootView: View =
            LayoutInflater.from(viewGroup!!.context).inflate(R.layout.spinner_layout, viewGroup, false)
        val text: TextView = rootView.findViewById(R.id.text_spinner);
        text.text = list[i].name
        return rootView;
    }
}