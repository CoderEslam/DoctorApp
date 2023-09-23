package com.doubleclick.doctorapp.android.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
import com.doubleclick.doctorapp.android.Model.PatientReservations.PatientOldReservation.MyPatientReservation
import com.doubleclick.doctorapp.android.Model.PatientReservations.PostPatientReservations
import com.doubleclick.doctorapp.android.Model.PatientReservations.ShowPatientOfDoctor.ShowPatientOfDoctor
import com.doubleclick.doctorapp.android.Model.Specialization.SpecializationList
import com.doubleclick.doctorapp.android.Repository.remot.RepositoryRemot
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call

class MainViewModel(private val repository: RepositoryRemot) : ViewModel() {

    private val loginResponseMutableLiveData: MutableLiveData<Call<LoginCallback>> =
        MutableLiveData()
    private val registerResponseMutableLiveData: MutableLiveData<Call<LoginCallback>> =
        MutableLiveData()
    private val updateUserMutableLiveData: MutableLiveData<Call<UpdateUser>> =
        MutableLiveData()
    private val days: MutableLiveData<Call<Days>> = MutableLiveData()
    private val favoriteDoctor: MutableLiveData<Call<FavoriteDoctor>> = MutableLiveData()

    /////////////////////////////Governorates/////////////////////////////////////
    private val araesList: MutableLiveData<Call<Araes>> = MutableLiveData()
    /////////////////////////////Governorates/////////////////////////////////////

    //////////////////////////assistants//////////////////////////////////
    private val assistantsList: MutableLiveData<Call<AssistantsList>> = MutableLiveData()
    //////////////////////////assistants//////////////////////////////////

    //////////////////////////patients//////////////////////////////////
    private val patientsList: MutableLiveData<Call<PatientsList>> = MutableLiveData()

    private val patient: MutableLiveData<Call<Patient>> = MutableLiveData()

    //////////////////////////patients//////////////////////////////////

    //////////////////////////Area//////////////////////////////////
    private val governoratesList: MutableLiveData<Call<Governorates>> = MutableLiveData()
    //////////////////////////Area//////////////////////////////////

    //////////////////////////message//////////////////////////////////
    private val message: MutableLiveData<Call<Message>> = MutableLiveData()
    //////////////////////////message//////////////////////////////////

    /////////////////////////Clinic//////////////////////////////////
    private val specializations: MutableLiveData<Call<SpecializationList>> = MutableLiveData()
    private val clinicList: MutableLiveData<Call<ClinicList>> = MutableLiveData()
    //////////////////////////Clinic//////////////////////////////////

    /////////////////////////general_specialties//////////////////////////////////
    private val general_specialties: MutableLiveData<Call<GeneralSpecializationList>> =
        MutableLiveData()
    //////////////////////////general_specialties//////////////////////////////////


    //////////////////////////Auth//////////////////////////////////
    fun getLoginResponse(login: Login): LiveData<Call<LoginCallback>> {
        loginResponseMutableLiveData.value = repository.loginAccount(login);
        return loginResponseMutableLiveData;
    }

    fun getRegisterResponse(registration: RegistrationUser): LiveData<Call<LoginCallback>> {
        registerResponseMutableLiveData.value =
            repository.createAccount(registration = registration);
        return registerResponseMutableLiveData;
    }

    fun updateAccount(token: String, updateAccount: UpdateAccount): LiveData<Call<UpdateUser>> {
        updateUserMutableLiveData.value = repository.updateAccount(token, updateAccount);
        return updateUserMutableLiveData;
    }


    //////////////////////////Auth//////////////////////////////////


    //////////////////////////Doctors//////////////////////////////////
    private val doctorsList: MutableLiveData<Call<DoctorsList>> = MutableLiveData()
    private val doctorsInfo: MutableLiveData<Call<DoctorsList>> = MutableLiveData()
    private val showPatientOfDoctor: MutableLiveData<Call<ShowPatientOfDoctor>> = MutableLiveData()
    private val myReservation: MutableLiveData<Call<MyPatientReservation>> = MutableLiveData()
    //////////////////////////Doctors//////////////////////////////////


    //////////////////////////Days at clinic//////////////////////////////////
    private val doctorsDaysAtClinicList: MutableLiveData<Call<DaysAtClinic>> = MutableLiveData()
    //////////////////////////Days at clinic//////////////////////////////////


    fun getDays(token: String): LiveData<Call<Days>> {
        days.value = repository.getDays(token = token);
        return days;
    }
    //////////////////////////////favorite_doctor/////////////////////////////////////////

    fun getFavoriteDoctor(token: String): LiveData<Call<FavoriteDoctor>> {
        favoriteDoctor.value = repository.getFavoriteDoctor(token = token);
        return favoriteDoctor;
    }

    fun putFavoriteDoctor(token: String, doctor_id: DoctorId): LiveData<Call<Message>> {
        message.value = repository.putFavoriteDoctor(token = token, doctor_id = doctor_id);
        return message;
    }

    fun deleteFavoriteDoctor(token: String, id: String): LiveData<Call<Message>> {
        message.value = repository.deleteFavoriteDoctor(token = token, id = id);
        return message;
    }

    //////////////////////////////favorite_doctor/////////////////////////////////////////

    /////////////////////////////Governorates/////////////////////////////////////

    fun getGovernoratesList(token: String): LiveData<Call<Governorates>> {
        governoratesList.value = repository.getGovernoratesList(token = token);
        return governoratesList;
    }

    /////////////////////////////Governorates/////////////////////////////////////


    //////////////////////////Area//////////////////////////////////
    fun getAreaList(token: String): LiveData<Call<Araes>> {
        araesList.value = repository.getAreaList(token = token);
        return araesList;
    }
    //////////////////////////Area//////////////////////////////////


    //////////////////////////Clinic//////////////////////////////////
    fun postClinic(token: String, store_clinic: AddClinics): LiveData<Call<Message>> {
        message.value = repository.postClinic(token = token, store_clinic = store_clinic);
        return message;
    }

    fun getClinicList(token: String): LiveData<Call<ClinicList>> {
        clinicList.value = repository.getClinicList(token = token);
        return clinicList;
    }

    //////////////////////////Clinic//////////////////////////////////


    //////////////////////////Specializations//////////////////////////////////
    fun getSpecializations(token: String): LiveData<Call<SpecializationList>> {
        specializations.value = repository.getSpecializations(token = token);
        return specializations;
    }
    //////////////////////////Specializations//////////////////////////////////

    //////////////////////////general_specialties//////////////////////////////////
    fun getGeneralSpecialties(token: String): LiveData<Call<GeneralSpecializationList>> {
        general_specialties.value = repository.getGeneralSpecialties(token = token);
        return general_specialties;
    }
    //////////////////////////general_specialties//////////////////////////////////

    //////////////////////////Doctor store//////////////////////////////////
//    fun postDoctor(token: String, doctor: Doctor): LiveData<Call<Message>> {
//        message.value = repository.postDoctor(token = token, doctor = doctor);
//        return message;
//    }

    fun updateDoctor(
        token: String,
        id: String,
        updateDoctor: UpdateDoctor
    ): LiveData<Call<Message>> {
        message.value =
            repository.updateDoctor(token = token, id = id, updateDoctor = updateDoctor);
        return message;
    }

    fun updateDoctorImage(
        token: String,
        id: String,
        image: MultipartBody.Part,
    ): LiveData<Call<Message>> {
        message.value = repository.updateDoctorImage(token = token, id = id, image = image);
        return message;
    }

    fun getPatientReservationDoctorList(
        token: String,
        id: String,
        date: String
    ): LiveData<Call<ShowPatientOfDoctor>> {
        showPatientOfDoctor.value = repository.getPatientReservationDoctorList(token = token, id = id, date = date);
        return showPatientOfDoctor;
    }

    // to see patients which visit this doctor by doctor id
    fun getPatientVisitDoctorList(
        token: String,
        id: String,
    ): LiveData<Call<MyPatientReservation>> {
        myReservation.value = repository.getPatientVisitDoctorList(token = token, id = id);
        return myReservation;
    }
    fun getHistoryPatientVisitDoctorList(
        token: String,
        id: String,
    ): LiveData<Call<MyPatientReservation>> {
        myReservation.value = repository.getHistoryPatientVisitDoctorList(token = token, id = id);
        return myReservation;
    }
    //////////////////////////Doctor store//////////////////////////////////


    //////////////////////////Doctors//////////////////////////////////
    fun getDoctorsList(token: String): LiveData<Call<DoctorsList>> {
        doctorsList.value = repository.getDoctorsList(token = token);
        return doctorsList;
    }

    fun getDoctorsInfoById(token: String, id: String): LiveData<Call<DoctorsList>> {
        doctorsInfo.value = repository.getDoctorsInfoById(token = token, id = id);
        return doctorsInfo;
    }

    fun searchDoctor(token: String, searchDoctor: SearchDoctor): LiveData<Call<DoctorsList>> {
        doctorsList.value = repository.searchDoctor(token = token, searchDoctor = searchDoctor);
        return doctorsList;
    }
    //////////////////////////Doctors//////////////////////////////////


    //////////////////////////Days at clinic//////////////////////////////////
    fun getDoctorDaysAtClinicList(token: String): LiveData<Call<DaysAtClinic>> {
        doctorsDaysAtClinicList.value = repository.getDoctorDaysAtClinicList(token = token);
        return doctorsDaysAtClinicList;
    }

    fun postDoctorDaysAtClinic(
        token: String,
        daysAtClinicModel: DaysAtClinicModel
    ): LiveData<Call<Message>> {
        message.value =
            repository.postDoctorDaysAtClinic(token = token, daysAtClinicModel = daysAtClinicModel);
        return message;
    }

    fun deleteDoctorDaysAtClinic(token: String, id: String): LiveData<Call<Message>> {
        message.value = repository.deleteDoctorDaysAtClinic(token = token, id = id);
        return message;
    }
    //////////////////////////Days at clinic//////////////////////////////////

    //////////////////////////patient//////////////////////////////////
    fun postFamilyMemberPatient(
        token: String,
        patientStore: PatientStore
    ): LiveData<Call<Message>> {
        message.value = repository.postPatient(token = token, patientStore = patientStore);
        return message;
    }

    fun getFamilyMemberPatient(token: String): LiveData<Call<PatientsList>> {
        patientsList.value = repository.getFamilyMemberPatient(token = token);
        return patientsList;
    }

    fun getPatientWithId(token: String, id: String): LiveData<Call<Patient>> {
        patient.value = repository.getPatientWithId(token = token, id = id);
        return patient;
    }
    //////////////////////////patient//////////////////////////////////


    //////////////////////////user image//////////////////////////////////
    fun postUserImage(token: String, image: MultipartBody.Part): LiveData<Call<Message>> {
        message.value = repository.postUserImage(token = token, image = image);
        return message;
    }
    //////////////////////////user image//////////////////////////////////

    //////////////////////////patient_visits//////////////////////////////////

    fun getPatientVisits(token: String, id: String) {

    }

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
    ): LiveData<Call<Message>> {
        message.value = repository.uploadPatientImages(
            token = token,
            type = type,
            doctor_id = doctor_id,
            patient_id = patient_id,
            clinic_id = clinic_id,
            reservation_date = reservation_date,
            patient_reservation_id = patient_reservation_id,
            diagnosis = diagnosis,
            images = images
        );
        return message;
    }
    //////////////////////////patient_visits//////////////////////////////////


    //////////////////////////assistants//////////////////////////////////

    fun postAssistants(token: String, addAssistants: AddAssistants): LiveData<Call<Message>> {
        message.value = repository.postAssistants(token = token, addAssistants = addAssistants)
        return message;
    }

    fun getAssistants(token: String): LiveData<Call<AssistantsList>> {
        assistantsList.value = repository.getAssistants(token = token)
        return assistantsList;
    }

    //////////////////////////assistants//////////////////////////////////


    //////////////////////////patient_reservations//////////////////////////////////
    fun postPatientReservations(
        token: String,
        patientReservationsModel: PostPatientReservations
    ): LiveData<Call<Message>> {
        message.value = repository.postPatientReservations(
            token = token,
            patientReservationsModel = patientReservationsModel
        )
        return message;
    }

    //////////////////////////patient_reservations//////////////////////////////////


}