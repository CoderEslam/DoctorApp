package com.doubleclick.doctorapp.android.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.bumptech.glide.Glide
import com.doubleclick.doctorapp.android.Model.Patient.PatientModel
import com.doubleclick.doctorapp.android.R
import com.doubleclick.doctorapp.android.views.CircleImageView

class SpinnerFamilyMemberAdapter(
    val patientModelList: List<PatientModel>
) : BaseAdapter() {
    override fun getCount(): Int = patientModelList.size

    override fun getItem(i: Int): Any = i

    override fun getItemId(i: Int): Long = i.toLong()

    override fun getView(i: Int, p1: View?, viewGroup: ViewGroup?): View {
        val rootView: View =
            LayoutInflater.from(viewGroup?.context).inflate(R.layout.spinner_layout, viewGroup, false)
        val text: TextView = rootView.findViewById(R.id.text_spinner);
        text.text = patientModelList[i].name
        return rootView;
    }
}