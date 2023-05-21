package com.example.bangkitandroid.data


import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.bangkitandroid.data.remote.response.CommonResponse
import com.example.bangkitandroid.data.remote.retrofit.ApiService
import com.example.bangkitandroid.domain.entities.User
import com.example.bangkitandroid.service.DummyData
import com.example.bangkitandroid.service.Result

class Repository (
    private val apiService: ApiService,
) {
    private val getUserResult = MediatorLiveData<Result<User>>()
    private val editProfileResult = MediatorLiveData<Result<CommonResponse>>()

    fun getUser(): LiveData<Result<User>> {
        getUserResult.value = Result.Success(DummyData().getUserDummy(1))
        return getUserResult
    }

    fun editProfile(name: String, phoneNumber: String): LiveData<Result<CommonResponse>> {
        editProfileResult.value = Result.Success(DummyData().generateDummyCommonResponse())
        return editProfileResult
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