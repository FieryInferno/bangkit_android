package com.example.bangkitandroid.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.bangkitandroid.data.remote.retrofit.ApiService
import com.example.bangkitandroid.domain.entities.User
import com.example.bangkitandroid.service.DummyData
import com.example.bangkitandroid.service.Result

class Repository(
    private val apiService: ApiService,
) {
    private val loginResult = MediatorLiveData<Result<User>>()
    private val registerResult = MediatorLiveData<Result<User>>()

    fun login(phoneNumber: String, password: String): LiveData<Result<User>> {
        loginResult.value = Result.Success(DummyData().getUserDummy(1))
        return loginResult
    }

    fun register(name: String, phoneNumber: String, password: String): LiveData<Result<User>> {
        registerResult.value = Result.Success(DummyData().getUserDummy(1))
        return registerResult
    }

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            apiService: ApiService,
        ): Repository = instance ?: synchronized(this){
            instance ?: Repository(apiService)
        }.also {
            instance = it
        }
    }
}