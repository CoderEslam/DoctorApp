package com.doubleclick.doctorapp.android.Repository.remot

import com.doubleclick.doctorapp.android.Model.*
import com.doubleclick.doctorapp.android.Model.Clinic.AddClinics
import com.doubleclick.doctorapp.android.Model.Area.Araes
import com.doubleclick.doctorapp.android.Model.Assistants.AddAssistants
import com.doubleclick.doctorapp.android.Model.Auth.Login
import com.doubleclick.doctorapp.android.Model.Auth.Registration
import com.doubleclick.doctorapp.android.Model.Auth.ResopnsLogin
import com.doubleclick.doctorapp.android.Model.Days.Days
import com.doubleclick.doctorapp.android.Model.Days.DaysAtClinic
import com.doubleclick.doctorapp.android.Model.Days.DaysAtClinicModel
import com.doubleclick.doctorapp.android.Model.Doctor.Doctor
import com.doubleclick.doctorapp.android.Model.Doctor.DoctorId
import com.doubleclick.doctorapp.android.Model.Doctor.DoctorInfo
import com.doubleclick.doctorapp.android.Model.Doctor.DoctorsList
import com.doubleclick.doctorapp.android.Model.Favorite.FavoriteDoctor
import com.doubleclick.doctorapp.android.Model.Governorates.Governorates
import com.doubleclick.doctorapp.android.Model.Patient.PatientStore
import com.doubleclick.doctorapp.android.Model.Specialization.Specialization
import com.doubleclick.doctorapp.android.api.RetrofitInstance
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call

class RepositoryRemot {

    fun loginAccount(login: Login): Call<ResopnsLogin> {
        return RetrofitInstance.api.login(login)
    }

    fun createAccount(registration: Registration): Call<ResopnsLogin> {
        return RetrofitInstance.api.createAccount(registration)
    }

    fun getDays(token: String): Call<Days> {
        return RetrofitInstance.api.getDays(token)
    }

    //////////////////////////////favorite_doctor/////////////////////////////////////////

    fun getFavoriteDoctor(token: String): Call<FavoriteDoctor> {
        return RetrofitInstance.api.getFavoriteDoctor(token = token)
    }

    fun putFavoriteDoctor(token: String, doctor_id: DoctorId): Call<Message> {
        return RetrofitInstance.api.putFavoriteDoctor(token = token, doctor_id = doctor_id)
    }

    fun deleteFavoriteDoctor(token: String, id: String): Call<Message> {
        return RetrofitInstance.api.deleteFavoriteDoctor(token = token, id = id)
    }

    //////////////////////////////favorite_doctor/////////////////////////////////////////

    /////////////////////////////Governorates/////////////////////////////////////
    fun getGovernoratesList(token: String): Call<Governorates> {
        return RetrofitInstance.api.getGovernoratesList(token = token)
    }

    /////////////////////////////Governorates/////////////////////////////////////

    //////////////////////////Area//////////////////////////////////
    fun getAreaList(token: String): Call<Araes> {
        return RetrofitInstance.api.getAreaList(token = token)
    }
    //////////////////////////Area//////////////////////////////////

    //////////////////////////Clinic//////////////////////////////////
    fun postClinic(token: String, store_clinic: AddClinics): Call<Message> {
        return RetrofitInstance.api.postClinic(token = token, store_clinic = store_clinic)
    }

    //////////////////////////Clinic//////////////////////////////////

    //////////////////////////Specializations//////////////////////////////////

    fun getSpecializations(token: String): Call<Specialization> {
        return RetrofitInstance.api.getSpecializations(token = token)
    }
    //////////////////////////Specializations//////////////////////////////////

    //////////////////////////general_specialties//////////////////////////////////
    fun getGeneralSpecialties(token: String): Call<Specialization> {
        return RetrofitInstance.api.getGeneralSpecialties(token = token)
    }
    //////////////////////////general_specialties//////////////////////////////////

    //////////////////////////Doctor store//////////////////////////////////
    fun postDoctor(token: String, doctor: Doctor): Call<Message> {
        return RetrofitInstance.api.postDoctor(token = token, doctor = doctor)
    }
    //////////////////////////Doctor store//////////////////////////////////


    //////////////////////////Doctors//////////////////////////////////
    fun getDoctorsList(token: String): Call<DoctorsList> {
        return RetrofitInstance.api.getDoctorsList(token = token)
    }

    fun getDoctorsInfoById(token: String, id: String): Call<DoctorsList> {
        return RetrofitInstance.api.getDoctorsInfoById(token = token, id = id)
    }

    //////////////////////////Doctors//////////////////////////////////


    //////////////////////////Days at clinic//////////////////////////////////
    fun getDoctorDaysAtClinicList(token: String): Call<DaysAtClinic> {
        return RetrofitInstance.api.getDoctorDaysAtClinicList(token = token)
    }

    fun postDoctorDaysAtClinic(token: String, daysAtClinicModel: DaysAtClinicModel): Call<Message> {
        return RetrofitInstance.api.postDoctorDaysAtClinic(
            token = token,
            daysAtClinicModel = daysAtClinicModel
        )
    }

    fun deleteDoctorDaysAtClinic(token: String, id: String): Call<Message> {
        return RetrofitInstance.api.deleteDoctorDaysAtClinic(token = token, id = id)
    }

    //////////////////////////Days at clinic//////////////////////////////////


    //////////////////////////patients//////////////////////////////////
    fun postPatient(token: String, patientStore: PatientStore): Call<Message> {
        return RetrofitInstance.api.postPatient(token = token, patientStore = patientStore)
    }


    //////////////////////////user image//////////////////////////////////
    fun postUserImage(token: String, image: MultipartBody.Part): Call<Message> {
        return RetrofitInstance.api.postUserImage(token = token, image = image)
    }
    //////////////////////////user image//////////////////////////////////


    //////////////////////////patient_visits//////////////////////////////////
    fun uploadPatientImages(
        token: String,
        type: RequestBody,
        doctor_id: RequestBody,
        patient_id: RequestBody,
        clinic_id: RequestBody,
        reservation_date: RequestBody,
        patient_reservation_id: RequestBody,
        diagnosis: RequestBody,
        images: List<MultipartBody.Part>
    ): Call<Message> {
        return RetrofitInstance.api.uploadPatientImages(
            token = token,
            type = type,
            doctor_id = doctor_id,
            patient_id = patient_id,
            clinic_id = clinic_id,
            reservation_date = reservation_date,
            patient_reservation_id = patient_reservation_id,
            diagnosis = diagnosis,
            images = images
        )
    }
    //////////////////////////patient_visits//////////////////////////////////

    //////////////////////////assistants//////////////////////////////////

    fun postAssistants(token: String, addAssistants: AddAssistants): Call<Message> {
        return RetrofitInstance.api.postAssistants(token = token, addAssistants = addAssistants)
    }

    //////////////////////////assistants//////////////////////////////////


}