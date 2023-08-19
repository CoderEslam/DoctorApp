package com.doubleclick.doctorapp.android.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.doctorapp.android.ItemNavigationListener
import com.doubleclick.doctorapp.android.Model.ItemNavigation
import com.doubleclick.doctorapp.android.Model.Patient.PatientModel
import com.doubleclick.doctorapp.android.Model.Patient.PatientsList
import com.doubleclick.doctorapp.android.R
import com.doubleclick.doctorapp.android.ViewHolders.FamilyMemberViewHolder
import com.doubleclick.doctorapp.android.ViewHolders.NavigationViewHolder

class AdapterFamilyMember(
    val patientModel: List<PatientModel>
) :
    RecyclerView.Adapter<FamilyMemberViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FamilyMemberViewHolder {
        return FamilyMemberViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_family_member, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FamilyMemberViewHolder, position: Int) {
        holder.name.text = patientModel[holder.absoluteAdapterPosition].name

    }

    override fun getItemCount(): Int = patientModel.size
}