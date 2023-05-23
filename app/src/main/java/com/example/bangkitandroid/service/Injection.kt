package com.example.bangkitandroid.service

import android.content.Context
import com.example.bangkitandroid.data.remote.Repository
import com.example.bangkitandroid.data.Repository
import com.example.bangkitandroid.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): Repository {
        val apiService = ApiConfig.getApiService()
        return Repository.getInstance(apiService)
    }
}