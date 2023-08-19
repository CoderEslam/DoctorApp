package com.doubleclick.doctorapp.android.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doubleclick.doctorapp.android.DeleteAssistant
import com.doubleclick.doctorapp.android.Model.Assistants.AssistantsModel
import com.doubleclick.doctorapp.android.R
import com.doubleclick.doctorapp.android.ViewHolders.DoctorAssistantViewHolder

class AdapterDoctorAssistant(
    val assistantsModelList: List<AssistantsModel>,
    val deleteAssistant: DeleteAssistant
) :
    RecyclerView.Adapter<DoctorAssistantViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorAssistantViewHolder {
        return DoctorAssistantViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_item_assistant, parent, false)
        )
    }

    override fun onBindViewHolder(holder: DoctorAssistantViewHolder, position: Int) {
        holder.assistant_name.text = assistantsModelList[holder.bindingAdapterPosition].name
        holder.assistant_phone.text = assistantsModelList[holder.bindingAdapterPosition].phone
        holder.delete_assistant.setOnClickListener {
            deleteAssistant.delete(assistantsModelList[holder.bindingAdapterPosition])
        }
    }

    override fun getItemCount(): Int = assistantsModelList.size
}