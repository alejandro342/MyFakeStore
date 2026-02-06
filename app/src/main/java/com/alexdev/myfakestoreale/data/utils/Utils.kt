package com.alexdev.myfakestoreale.data.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

// 1.Una única instancia de DataStore
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")
