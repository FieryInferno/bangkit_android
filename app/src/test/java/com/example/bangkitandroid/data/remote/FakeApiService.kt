package com.example.bangkitandroid.data.remote

import com.example.bangkitandroid.data.remote.response.LoginResponse
import com.example.bangkitandroid.data.remote.response.RegisterResponse
import com.example.bangkitandroid.data.remote.retrofit.ApiService
import com.example.bangkitandroid.service.DummyData

class FakeApiService : ApiService {
    override fun login(phoneNumber: String, password: String): LoginResponse {
        return DummyData().generateLoginResponse()
    }

    override fun register(name: String, phoneNumber: String, password: String): RegisterResponse {
        return DummyData().generateRegisterResponse()
    }
}