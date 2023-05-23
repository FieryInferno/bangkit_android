package com.example.bangkitandroid.data.remote

import com.example.bangkitandroid.data.remote.response.DiseaseHistoryResponse
import com.example.bangkitandroid.data.remote.response.DiseaseResponse
import com.example.bangkitandroid.data.remote.response.LoginResponse
import com.example.bangkitandroid.data.remote.response.RegisterResponse
import com.example.bangkitandroid.data.remote.retrofit.ApiService
import com.example.bangkitandroid.service.DummyData
import okhttp3.MultipartBody

class FakeApiService : ApiService {
    override fun getDisease(token: String, id: Int): DiseaseResponse {
        if(token == ""){
            return DiseaseResponse(
                success = false,
                message = "invalid token",
                disease = null
            )
        }
        return DiseaseResponse(
            success = true,
            message = "success",
            disease = DummyData().getDetailDisease(id)
        )
    }

    override fun getDiseases(token: String, page: Int, size: Int): DiseaseHistoryResponse {
        return DiseaseHistoryResponse(
            success = true,
            message = "success",
            disease = DummyData().getHistoryDiseases()
        )
    }

    override fun postDisease(token: String, file: MultipartBody.Part): DiseaseResponse {
        if(token == ""){
            return DiseaseResponse(
                success = false,
                message = "invalid token",
                disease = null
            )
        }
        return DiseaseResponse(
            success = true,
            message = "success",
            disease = DummyData().getDetailDisease(0)
        )
    }

    override fun login(phoneNumber: String, password: String): LoginResponse {
        return LoginResponse(
            success = true,
            message = "Success",
            data = DummyData().getUser(0)
        )
    }

    override fun register(name: String, phoneNumber: String, password: String): RegisterResponse {
        return RegisterResponse(
            success = true,
            message = "Success",
            data = DummyData().getUser(0)
        )
    }
}
