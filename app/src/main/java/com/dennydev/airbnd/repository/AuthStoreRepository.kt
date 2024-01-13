package com.dennydev.airbnd.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = "auth")
@ViewModelScoped
class AuthStoreRepository @Inject constructor(@ApplicationContext context: Context) {

    private val dataStore = context.dataStore

    val flowToken: Flow<String> = dataStore.data
        .map { preferences ->
            // No type safety.
            preferences[tokenKey] ?: ""
        }

    val flowLoginGoogle: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[googleKey] ?: false
    }

    suspend fun saveToken(token: String, isGoogle: Boolean) {
        dataStore.edit { settings ->
            settings[tokenKey] = token
            settings[googleKey] = isGoogle
        }
    }

    suspend fun removeToken(){
        dataStore.edit {
            it[tokenKey] = ""
            it[googleKey] = false
        }
    }

    companion object PreferencesKey{
        val tokenKey = stringPreferencesKey("_token")
        val googleKey = booleanPreferencesKey("_google")
    }
}