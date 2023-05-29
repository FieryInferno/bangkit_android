package com.example.bangkitandroid.service

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.bangkitandroid.data.local.TokenPreferences
import com.example.bangkitandroid.data.remote.Repository
import com.example.bangkitandroid.data.remote.retrofit.ApiConfig

object Injection {
    private const val TOKEN = "token"
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = TOKEN)
    fun provideRepository(context: Context): Repository {
        val apiService = ApiConfig.getApiService()
        val tokenPreferences = TokenPreferences.getInstance(context.dataStore)
        return Repository.getInstance(apiService, tokenPreferences)
    }
}