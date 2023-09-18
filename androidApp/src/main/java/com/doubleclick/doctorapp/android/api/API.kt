package com.doubleclick.doctorapp.android.api

import com.doubleclick.doctorapp.android.Model.*
import com.doubleclick.doctorapp.android.Model.Clinic.AddClinics
import com.doubleclick.doctorapp.android.Model.Area.Araes
import com.doubleclick.doctorapp.android.Model.Area.StoreArea
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
import com.doubleclick.doctorapp.android.Model.Governorates.StoreGovernorates
import com.doubleclick.doctorapp.android.Model.MedicalAdvice.MedicalAdvice
import com.doubleclick.doctorapp.android.Model.Patient.Patient
import com.doubleclick.doctorapp.android.Model.Patient.PatientStore
import com.doubleclick.doctorapp.android.Model.Patient.PatientsList
import com.doubleclick.doctorapp.android.Model.PatientReservations.PatientReservationsList
import com.doubleclick.doctorapp.android.Model.PatientReservations.PostPatientReservations
import com.doubleclick.doctorapp.android.Model.Specialization.SpecializationList
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface API {

    @Headers("Content-Type: application/json")
    @POST("login")
    fun login(
        @Body login: Login
    ): Call<LoginCallback>

    @Headers("Content-Type: application/json")
    @POST("update_user_info")
    fun updateAccount(
        @Header("Authorization") token: String,
        @Body updateAccount: UpdateAccount
    ): Call<UpdateUser>

    @Headers("Content-Type: application/json")
    @POST("register")
    fun createAccount(
        @Body registration: RegistrationUser
    ): Call<LoginCallback>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET("days")
    fun getDays(
        @Header("Authorization") token: String,
    ): Call<Days>

    //////////////////////////////favorite_doctor/////////////////////////////////////////
    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET("favorite_doctor")
    fun getFavoriteDoctor(
        @Header("Authorization") token: String,
    ): Call<FavoriteDoctor>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("favorite_doctor")
    fun putFavoriteDoctor(
        @Header("Authorization") token: String,
        @Body doctor_id: DoctorId
    ): Call<Message>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @DELETE("favorite_doctor/{id}")
    fun deleteFavoriteDoctor(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Call<Message>
    //////////////////////////////favorite_doctor/////////////////////////////////////////

    //////////////////////////Area//////////////////////////////////
    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET("areas")
    fun getAreaList(@Header("Authorization") token: String): Call<Araes>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("areas")
    fun postArea(@Body store_area: StoreArea): Call<Message>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @PUT("areas")
    fun putArea(@Body update_area: JsonObject): Call<Message>
    //////////////////////////Area//////////////////////////////////


    //////////////////////////Governorates//////////////////////////////////

    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET("governorates")
    fun getGovernoratesList(
        @Header("Authorization") token: String,
    ): Call<Governorates>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("governorates")
    fun postGovernorates(
        @Header("Authorization") token: String, @Body store_governorates: StoreGovernorates
    ): Call<Message>
    //////////////////////////Governorates//////////////////////////////////


    //////////////////////////Clinic//////////////////////////////////
    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("clinics")
    fun postClinic(
        @Header("Authorization") token: String, @Body store_clinic: AddClinics
    ): Call<Message>


    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET("clinics")
    fun getClinicList(
        @Header("Authorization") token: String,
    ): Call<ClinicList>


    //////////////////////////Clinic//////////////////////////////////

    //////////////////////////Specializations//////////////////////////////////
    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET("specializations")
    fun getSpecializations(
        @Header("Authorization") token: String
    ): Call<SpecializationList>
    //////////////////////////Specializations//////////////////////////////////


    //////////////////////////general_specialties//////////////////////////////////
    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET("general_specialties")
    fun getGeneralSpecialties(
        @Header("Authorization") token: String
    ): Call<GeneralSpecializationList>

    //////////////////////////general_specialties//////////////////////////////////


    //////////////////////////Doctors//////////////////////////////////
    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET("doctors")
    fun getDoctorsList(
        @Header("Authorization") token: String,
    ): Call<DoctorsList>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET("doctors/{id}")
    fun getDoctorsInfoById(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Call<DoctorsList>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("search")
    fun searchDoctor(
        @Header("Authorization") token: String,
        @Body searchDoctor: SearchDoctor
    ): Call<DoctorsList>

//    @Headers("Content-Type: application/json", "Accept: application/json")
//    @POST("doctors")
//    fun postDoctor(
//        @Header("Authorization") token: String, @Body doctor: Doctor
//    ): Call<Message>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @PUT("doctors/{id}")
    fun updateDoctor(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body updateDoctor: UpdateDoctor
    ): Call<Message>

    @Multipart
    @POST("doctor/{id}/upload_image")
    fun updateDoctorImage(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Part image: MultipartBody.Part,
    ): Call<Message>


    //////////////////////////Doctors//////////////////////////////////

    //////////////////////////Days at clinic//////////////////////////////////
    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET("doctor_days_at_clinic")
    fun getDoctorDaysAtClinicList(
        @Header("Authorization") token: String,
    ): Call<DaysAtClinic>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("doctor_days_at_clinic")
    fun postDoctorDaysAtClinic(
        @Header("Authorization") token: String,
        @Body daysAtClinicModel: DaysAtClinicModel
    ): Call<Message>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @DELETE("doctor_days_at_clinic/{id}")
    fun deleteDoctorDaysAtClinic(
        @Header("Authorization") token: String,
        @Path("id") id: String,
    ): Call<Message>

    //////////////////////////Days at clinic//////////////////////////////////


    //////////////////////////patients//////////////////////////////////
    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("patients")
    fun postPatient(
        @Header("Authorization") token: String,
        @Body patientStore: PatientStore
    ): Call<Message>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET("patients")
    fun getFamilyMemberPatient(
        @Header("Authorization") token: String,
    ): Call<PatientsList>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET("patients/{id}")
    fun getPatientWithId(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Call<Patient>

    //////////////////////////patients//////////////////////////////////


    //////////////////////////user image//////////////////////////////////
    @Multipart
    @POST("user/upload_image")
    fun postUserImage(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part,
    ): Call<Message>
    //////////////////////////user image//////////////////////////////////


    //////////////////////////patient_visits//////////////////////////////////

    @GET("patient_visits/{id}")
    fun getPatientVisits(
        @Header("Authorization") token: String,
        @Path("id") id: String,
    ): Call<Message>

    @Multipart
    @POST("patient_visits")
    fun postPatientImages(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part,
    ): Call<Message>


    @Multipart
    @POST("patient_visits")
    fun uploadPatientImages(
        @Header("Authorization") token: String,
        @Part("type") type: RequestBody,
        @Part("doctor_id") doctor_id: RequestBody,
        @Part("patient_id") patient_id: RequestBody,
        @Part("clinic_id") clinic_id: RequestBody,
        @Part("reservation_date") reservation_date: RequestBody,
        @Part("patient_reservation_id") patient_reservation_id: RequestBody,
        @Part("diagnosis") diagnosis: RequestBody,
        @Part images: List<MultipartBody.Part>,
    ): Call<Message>
    //////////////////////////patient_visits//////////////////////////////////


    //////////////////////////assistants//////////////////////////////////
    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("assistants")
    fun postAssistants(
        @Header("Authorization") token: String,
        @Body addAssistants: AddAssistants
    ): Call<Message>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET("assistants")
    fun getAssistants(
        @Header("Authorization") token: String,
    ): Call<AssistantsList>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @DELETE("assistants/{id}")
    fun deleteAssistants(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Call<Message>


    //////////////////////////assistants//////////////////////////////////


    //////////////////////////patient_reservations//////////////////////////////////
    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("patient_reservations")
    fun postPatientReservations(
        @Header("Authorization") token: String,
        @Body patientReservationsModel: PostPatientReservations
    ): Call<Message>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET("patient_reservations/patient/{id}")
    fun getPatientReservations(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Call<PatientReservationsList>
    //////////////////////////patient_reservations//////////////////////////////////

    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET("medical_advices")
    fun getMedicalAdvices(
        @Header("Authorization") token: String,
    ): Call<MedicalAdvice>

    @Multipart
    @POST("medical_advices")
    fun postMedicalAdvices(
        @Header("Authorization") token: String,
        @Part video: MultipartBody.Part
    ): Call<Message>


}