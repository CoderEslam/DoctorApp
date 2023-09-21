package com.doubleclick.doctorapp.android.Adapters

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.doubleclick.doctorapp.android.Model.PatientReservations.PatientOldReservation.MyPatientReservation
import com.doubleclick.doctorapp.android.Model.PatientReservations.PatientOldReservation.ReservationModel
import com.doubleclick.doctorapp.android.Model.PatientReservations.ShowPatientOfDoctor.ShowPatientOfDoctorModel
import com.doubleclick.doctorapp.android.R
import com.doubleclick.doctorapp.android.ViewHolders.DoctorReservationViewHolder
import com.doubleclick.doctorapp.android.utils.collapse
import com.doubleclick.doctorapp.android.utils.expand
import com.doubleclick.doctorapp.android.utils.isNotNullOrEmptyString
import java.time.LocalDate
import java.time.Period

class MyPatientOfDoctorAdapter(val reservationModelList: List<ReservationModel>) :
    RecyclerView.Adapter<DoctorReservationViewHolder>() {
    private val TAG = "DoctorReservationAdapte"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorReservationViewHolder {
        return DoctorReservationViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.layout_item_reservation,
                    parent,
                    false
                )
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: DoctorReservationViewHolder, position: Int) {
        var images = listOf<String>()
        if (reservationModelList[holder.bindingAdapterPosition].images?.isNotNullOrEmptyString() == true) {
            images = reservationModelList[holder.bindingAdapterPosition].images?.split(",")!!
        }
        if (reservationModelList[holder.bindingAdapterPosition].patient_reservation?.age?.isNotNullOrEmptyString() == true) {
            val age =
                reservationModelList[holder.bindingAdapterPosition].patient_reservation?.age?.split(
                    "/"
                )
            holder.stack_item_image.adapter = StackViewAdapter(holder.itemView.context, images)
            val dob: LocalDate = LocalDate.of(age!![0].toInt(), age[1].toInt(), age[2].toInt())
            val curDate: LocalDate = LocalDate.now()
            val period: Period = Period.between(dob, curDate)
            holder.patent_age.text = buildString {
                append("Your age is ")
                append(period.years)
                append(" years ,")
                append(period.months)
                append(" months ,")
                append(period.days)
                append(" days ,")
//                append(reservationModelList[holder.bindingAdapterPosition].patient_reservation?.age)
            }
        } else {
            holder.patent_age.text = buildString {
                append("--")
            }
        }
        holder.patent_name.text = buildString {
            append(reservationModelList[holder.bindingAdapterPosition].patient?.name)
        }
        holder.patent_phone.text = buildString {
            append(
                if (reservationModelList[holder.bindingAdapterPosition].patient_reservation?.patient_phone
                        ?.isNotNullOrEmptyString() == true
                ) reservationModelList[holder.bindingAdapterPosition].patient_reservation?.patient_phone else "--"
            )
        }

        holder.patent_reservation_time.text = buildString {
            append(reservationModelList[holder.bindingAdapterPosition].patient_reservation?.reservation_date)
        }
        holder.patent_blood.text = buildString {
            append(reservationModelList[holder.bindingAdapterPosition].patient?.blood_type)
        }
        holder.patent_smoking.text = buildString {
            append(holder.smokingList[reservationModelList[holder.bindingAdapterPosition].patient?.smoking!!])
        }
        holder.patent_marital_status.text = buildString {
            append(holder.materielStatusList[reservationModelList[holder.bindingAdapterPosition].patient?.materiel_status!!])
        }
        holder.patent_drinking_alcohol.text = buildString {
            append(holder.alcoholDrinkingList[reservationModelList[holder.bindingAdapterPosition].patient?.alcohol_drinking!!])
        }
        holder.patent_height.text = buildString {
            append(reservationModelList[holder.bindingAdapterPosition].patient?.height)

        }
        holder.patent_weight.text = buildString {
            append(reservationModelList[holder.bindingAdapterPosition].patient?.weight)
        }
        holder.itemView.setOnClickListener {
            if (holder.more_info.visibility == View.GONE) {
                holder.itemView.expand(holder.more_info)
                holder.arrow_open_more.animate().rotation(180f).start()
                holder.more_info.visibility = View.VISIBLE
            } else {
                holder.itemView.collapse(holder.more_info)
                holder.arrow_open_more.animate().rotation(0f).start()
            }
        }

    }

    override fun getItemCount(): Int {
        return reservationModelList.size
    }
}