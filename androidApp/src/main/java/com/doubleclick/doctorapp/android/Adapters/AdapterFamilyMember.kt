package com.doubleclick.doctorapp.android.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.doctorapp.android.FamilyOption
import com.doubleclick.doctorapp.android.ItemNavigationListener
import com.doubleclick.doctorapp.android.Model.ItemNavigation
import com.doubleclick.doctorapp.android.Model.Patient.PatientModel
import com.doubleclick.doctorapp.android.Model.Patient.PatientsList
import com.doubleclick.doctorapp.android.R
import com.doubleclick.doctorapp.android.ViewHolders.FamilyMemberViewHolder
import com.doubleclick.doctorapp.android.ViewHolders.NavigationViewHolder

class AdapterFamilyMember(
    val familyOption: FamilyOption,
    val patientModel: List<PatientModel>
) :
    RecyclerView.Adapter<FamilyMemberViewHolder>() {

    private val smokingList = listOf("No", "Yes")
    private val materielStatusList = listOf("Single", "Married", "Widower", "Divorced")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FamilyMemberViewHolder {
        return FamilyMemberViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_family_member, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FamilyMemberViewHolder, position: Int) {
        holder.name.text = patientModel[holder.absoluteAdapterPosition].name
        holder.smoking.text = smokingList[patientModel[holder.absoluteAdapterPosition].smoking ?: 0]
        holder.blood_type.text = patientModel[holder.absoluteAdapterPosition].blood_type
        holder.materiel_status.text =
            materielStatusList[patientModel[holder.absoluteAdapterPosition].materiel_status ?: 0]
        holder.phone.text = patientModel[holder.absoluteAdapterPosition].phone

        holder.more.setOnClickListener {
            val popupMenu = PopupMenu(holder.itemView.context, it)
            popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.patient_reservations ->
                        familyOption.patientReservations(patientModel[holder.absoluteAdapterPosition].id.toString())
                    R.id.patient_visits ->
                        familyOption.patientVisits(patientModel[holder.absoluteAdapterPosition].id.toString())
                }
                true
            }
            popupMenu.show()
        }
    }

    override fun getItemCount(): Int = patientModel.size
}