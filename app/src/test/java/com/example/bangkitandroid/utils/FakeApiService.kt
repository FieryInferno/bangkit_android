package com.example.bangkitandroid.utils

import com.example.bangkitandroid.data.remote.response.EditProfileResponse
import com.example.bangkitandroid.data.remote.response.DiseaseHistoryResponse
import com.example.bangkitandroid.data.remote.response.ListBlogResponse
import com.example.bangkitandroid.data.remote.retrofit.ApiService
import com.example.bangkitandroid.service.DummyData

class FakeApiService : ApiService {
    override fun editProfile(token: String, name: String, phoneNumber: String): EditProfileResponse {
        return DummyData().generateEditProfileResponse()
    }

    override fun getDiseases(token: String, page: Int, size: Int): DiseaseHistoryResponse {
        return DiseaseHistoryResponse(
            status = true,
            message = "success",
            diseases = DummyData().getHistoryDiseases()
        )
    }

    override fun getBlogs(page: Int, size: Int): ListBlogResponse {
        return ListBlogResponse(
            status = true,
            message = "success",
            blogs = DummyData().getListBlogs()
        )
    }
}