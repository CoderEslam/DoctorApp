package com.doubleclick.doctorapp.android.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.doubleclick.doctorapp.android.utils.DataStore.dataStore
import kotlinx.coroutines.flow.first


/**
 * Created By Eslam Ghazy on 12/13/2022
 */
object SessionManger {

    private val TAG = "SsuessionManger"

    suspend fun Context.updateSession(
        token: String,
        email: String,
        password: String,
        id: String,
        name: String,
        phone: String
    ) {
        val jwtTokenKey = stringPreferencesKey(ConstantsSession.TOKEN_KEY.text)
        val passwordKey = stringPreferencesKey(ConstantsSession.PASSWORD_KEY.text)
        val emailKey = stringPreferencesKey(ConstantsSession.EMAIL_KEY.text)
        val idKey = stringPreferencesKey(ConstantsSession.ID_KEY.text)
        val nameKey = stringPreferencesKey(ConstantsSession.NAME_KEY.text)
        val phoneKey = stringPreferencesKey(ConstantsSession.PHONE_KEY.text)

        /*
        * to save name , email , token in local phone by preferences
        * */

        /*updateSession: token: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJOb3RlQXV0aCIsImlzcyI6Im5vdGVTZXJ2ZXIiLCJlbWFpbCI6ImVzbGFtZ2hhenk1NTVAZ21haWwuY29tIn0.pcy21quPrOSmzJ_RcbdP55e_gaTGANgH8SeZBt7kRlO4wO3zPP5GBRp671LcugASvzUbzu6eQrNuflRfva1jWA name: Eslam Ghazy email: eslamghazy555@gmail.com*/

        dataStore.edit { preferences ->
            preferences[jwtTokenKey] = token
            preferences[passwordKey] = password
            preferences[emailKey] = email
            preferences[idKey] = id
            preferences[nameKey] = name
            preferences[phoneKey] = phone
        }


    }


    suspend fun Context.doctorId(
        id: String,
    ) {
        val id_doctor = stringPreferencesKey(ConstantsSession.ID_DOCTOR.text)
        /*
        * to save name , email , token in local phone by preferences
        * */
        dataStore.edit { preferences ->
            preferences[id_doctor] = id
        }
    }

    suspend fun Context.getDoctorId(): String? {
        val id_doctor = stringPreferencesKey(ConstantsSession.ID_DOCTOR.text)
        val preferences = dataStore.data.first()
        return /*preferences[id_doctor]*/"3"
    }

    /*
    * to get token stored in preferences by (key name)
    * */
    suspend fun Context.getToken(): String? {
        val jwtTokenKey = stringPreferencesKey(ConstantsSession.TOKEN_KEY.text)
        val preferences = dataStore.data.first()
        return preferences[jwtTokenKey]
    }

    suspend fun Context.getName(): String? {
        val nameKey = stringPreferencesKey(ConstantsSession.NAME_KEY.text)
        val preferences = dataStore.data.first()
        return preferences[nameKey]
    }

    suspend fun Context.getPhone(): String? {
        val phoneKey = stringPreferencesKey(ConstantsSession.PHONE_KEY.text)
        val preferences = dataStore.data.first()
        return preferences[phoneKey]
    }

    suspend fun Context.getId(): String? {
        val idKey = stringPreferencesKey(ConstantsSession.ID_KEY.text)
        val preferences = dataStore.data.first()
        return preferences[idKey]
    }

    suspend fun Context.getImage(): String? {
        val imageKey = stringPreferencesKey(ConstantsSession.IMAGE_KEY.text)
        val preferences = dataStore.data.first()
        return preferences[imageKey]
    }

    suspend fun Context.setImage(image: String) {
        val imageKey = stringPreferencesKey(ConstantsSession.IMAGE_KEY.text)
        dataStore.edit { preferences ->
            preferences[imageKey] = image
        }
    }

    /*
    * to get password stored in preferences by (key name)
    * */
    suspend fun Context.getCurrentPassword(): String? {
        val passwordKey = stringPreferencesKey(ConstantsSession.PASSWORD_KEY.text)
        val preferences = dataStore.data.first()
        return preferences[passwordKey]
    }

    /*
    * to get id stored in preferences by (key name)
    * */
    suspend fun Context.getCurrentUserEmail(): String? {
        val emailKey = stringPreferencesKey(ConstantsSession.EMAIL_KEY.text)
        val preferences = dataStore.data.first()
        return preferences[emailKey]
    }


    /*
    * delete all data by logout
    * */
    suspend fun Context.logoutManger() {
        dataStore.edit {
            it.clear()
        }
    }

}