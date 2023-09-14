package com.doubleclick.doctorapp.android.Repository.remot

import com.doubleclick.doctorapp.android.Model.*
import com.doubleclick.doctorapp.android.Model.Clinic.AddClinics
import com.doubleclick.doctorapp.android.Model.Area.Araes
import com.doubleclick.doctorapp.android.Model.Assistants.AddAssistants
import com.doubleclick.doctorapp.android.Model.Assistants.AssistantsList
import com.doubleclick.doctorapp.android.Model.Auth.*
import com.doubleclick.doctorapp.android.Model.Clinic.ClinicList
import com.doubleclick.doctorapp.android.Model.Days.Days
import com.doubleclick.doctorapp.android.Model.Days.DaysAtClinic
import com.doubleclick.doctorapp.android.Model.Days.DaysAtClinicModel
import com.doubleclick.doctorapp.android.Model.Doctor.*
import com.doubleclick.doctorapp.android.Model.Favorite.FavoriteDoctor
import com.doubleclick.doctorapp.android.Model.GeneralSpecialization.GeneralSpecializationList
import com.doubleclick.doctorapp.android.Model.Governorates.Governorates
import com.doubleclick.doctorapp.android.Model.Patient.Patient
import com.doubleclick.doctorapp.android.Model.Patient.PatientStore
import com.doubleclick.doctorapp.android.Model.Patient.PatientsList
import com.doubleclick.doctorapp.android.Model.PatientReservations.PostPatientReservations
import com.doubleclick.doctorapp.android.Model.Specialization.SpecializationList
import com.doubleclick.doctorapp.android.api.RetrofitInstance
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call

class RepositoryRemot {

    fun loginAccount(login: Login): Call<LoginCallback> {
        return RetrofitInstance.api.login(login)
    }

    fun createAccount(registration: RegistrationUser): Call<LoginCallback> {
        return RetrofitInstance.api.createAccount(registration)
    }

    fun updateAccount(token: String, updateAccount: UpdateAccount): Call<UpdateUser> {
        return RetrofitInstance.api.updateAccount(token, updateAccount)
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

    fun getClinicList(token: String): Call<ClinicList> {
        return RetrofitInstance.api.getClinicList(token = token)
    }

    //////////////////////////Clinic//////////////////////////////////

    //////////////////////////Specializations//////////////////////////////////

    fun getSpecializations(token: String): Call<SpecializationList> {
        return RetrofitInstance.api.getSpecializations(token = token)
    }
    //////////////////////////Specializations//////////////////////////////////

    //////////////////////////general_specialties//////////////////////////////////
    fun getGeneralSpecialties(token: String): Call<GeneralSpecializationList> {
        return RetrofitInstance.api.getGeneralSpecialties(token = token)
    }
    //////////////////////////general_specialties//////////////////////////////////

    //////////////////////////Doctor store//////////////////////////////////
//    fun postDoctor(token: String, doctor: Doctor): Call<Message> {
//        return RetrofitInstance.api.postDoctor(token = token, doctor = doctor)
//    }

    fun updateDoctor(token: String, id: String, updateDoctor: UpdateDoctor): Call<Message> {
        return RetrofitInstance.api.updateDoctor(
            token = token,
            id = id,
            updateDoctor = updateDoctor
        )
    }

    fun updateDoctorImage(
        token: String,
        id: String,
        image: MultipartBody.Part,
    ): Call<Message> {
        return RetrofitInstance.api.updateDoctorImage(
            token = token,
            id = id,
            image = image,
        )
    }
    //////////////////////////Doctor store//////////////////////////////////


    //////////////////////////Doctors//////////////////////////////////
    fun getDoctorsList(token: String): Call<DoctorsList> {
        return RetrofitInstance.api.getDoctorsList(token = token)
    }

    fun getDoctorsInfoById(token: String, id: String): Call<DoctorsList> {
        return RetrofitInstance.api.getDoctorsInfoById(token = token, id = id)
    }

    fun searchDoctor(token: String, searchDoctor: SearchDoctor): Call<DoctorsList> {
        return RetrofitInstance.api.searchDoctor(token = token, searchDoctor = searchDoctor)
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

    fun getFamilyMemberPatient(token: String): Call<PatientsList> {
        return RetrofitInstance.api.getFamilyMemberPatient(token = token)
    }

    fun getPatientWithId(token: String, id: String): Call<Patient> {
        return RetrofitInstance.api.getPatientWithId(token = token, id = id)
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

    fun getPatientVisits(token: String, id: String): Call<Message> {
        return RetrofitInstance.api.getPatientVisits(token = token, id = id)
    }
    //////////////////////////patient_visits//////////////////////////////////

    //////////////////////////assistants//////////////////////////////////

    fun postAssistants(token: String, addAssistants: AddAssistants): Call<Message> {
        return RetrofitInstance.api.postAssistants(token = token, addAssistants = addAssistants)
    }

    fun getAssistants(token: String): Call<AssistantsList> {
        return RetrofitInstance.api.getAssistants(token = token)
    }

    //////////////////////////assistants//////////////////////////////////


    //////////////////////////patient_reservations//////////////////////////////////
    fun postPatientReservations(
        token: String,
        patientReservationsModel: PostPatientReservations
    ): Call<Message> {
        return RetrofitInstance.api.postPatientReservations(
            token = token,
            patientReservationsModel = patientReservationsModel
        )
    }
    //////////////////////////patient_reservations//////////////////////////////////


}