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

    private val TAG = "SessionManger"

    suspend fun Context.updateSession(
        token: String,
        email: String,
        password: String,
        user_id: String,
        name: String,
        phone: String,
        role: String,
    ) {
        val jwtTokenKey = stringPreferencesKey(ConstantsSession.TOKEN_KEY.text)
        val passwordKey = stringPreferencesKey(ConstantsSession.PASSWORD_KEY.text)
        val emailKey = stringPreferencesKey(ConstantsSession.EMAIL_KEY.text)
        val idKey = stringPreferencesKey(ConstantsSession.ID_KEY.text)
        val nameKey = stringPreferencesKey(ConstantsSession.NAME_KEY.text)
        val phoneKey = stringPreferencesKey(ConstantsSession.PHONE_KEY.text)
        val roleKey = stringPreferencesKey(ConstantsSession.ROLE.text)
        /*
        * to save name , email , token in local phone by preferences
        * */
        dataStore.edit { preferences ->
            preferences[jwtTokenKey] = token
            preferences[passwordKey] = password
            preferences[emailKey] = email
            preferences[idKey] = user_id
            preferences[nameKey] = name
            preferences[phoneKey] = phone
            preferences[roleKey] = role
        }


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

    suspend fun Context.setName(name: String) {
        val nameKey = stringPreferencesKey(ConstantsSession.NAME_KEY.text)
        dataStore.edit { preferences ->
            preferences[nameKey] = name
        }
    }

    suspend fun Context.setToken(token: String) {
        val tokenKey = stringPreferencesKey(ConstantsSession.TOKEN_KEY.text)
        dataStore.edit { preferences ->
            preferences[tokenKey] = token
        }
    }

    suspend fun Context.setPhone(phone: String) {
        val phoneKey = stringPreferencesKey(ConstantsSession.PHONE_KEY.text)
        dataStore.edit { preferences ->
            preferences[phoneKey] = phone
        }
    }

    suspend fun Context.setEmail(email: String) {
        val emailKey = stringPreferencesKey(ConstantsSession.EMAIL_KEY.text)
        dataStore.edit { preferences ->
            preferences[emailKey] = email
        }
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

    suspend fun Context.getRole(): String? {
        val roleKey = stringPreferencesKey(ConstantsSession.ROLE.text)
        val preferences = dataStore.data.first()
        return preferences[roleKey]
    }

    suspend fun Context.setIdWorker(id: String) {
        val idKey = stringPreferencesKey(ConstantsSession.ID_WORKER.text)
        dataStore.edit { preferences ->
            preferences[idKey] = id
        }
    }

    suspend fun Context.getIdWorker(): String? {
        val idKey = stringPreferencesKey(ConstantsSession.ID_WORKER.text)
        val preferences = dataStore.data.first()
        return preferences[idKey]
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