package com.example.bangkitandroid.utils

import com.example.bangkitandroid.data.remote.response.CommonResponse
import com.example.bangkitandroid.data.remote.retrofit.ApiService
import com.example.bangkitandroid.service.DummyData

class FakeApiService : ApiService {
    override fun editProfile(token: String, name: String, phoneNumber: String): CommonResponse {
        return DummyData().generateSuccessCommonResponse()
    }
}