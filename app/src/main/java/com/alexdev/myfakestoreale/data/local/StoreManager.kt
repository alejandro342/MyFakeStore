package com.alexdev.myfakestoreale.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.alexdev.myfakestoreale.data.utils.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoreManager(private val context: Context) {

    companion object {
        private val USER_TOKEN_KEY = stringPreferencesKey("jwt_token")
        private val USER_NAME_KEY = stringPreferencesKey("user_name") // <--- NUEVA LLAVE
    }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_TOKEN_KEY] = token
        }
    }
    suspend fun saveSession(token: String, username: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_TOKEN_KEY] = token
            preferences[USER_NAME_KEY] = username
        }
    }

    fun getToken(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[USER_TOKEN_KEY]
        }
    }
    fun getUser(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[USER_NAME_KEY]
        }
    }

    suspend fun clearSession() {
        context.dataStore.edit { preferences ->
            preferences.remove(USER_TOKEN_KEY)
            preferences.remove(USER_NAME_KEY)
            // preferences.clear()
        }
    }
}