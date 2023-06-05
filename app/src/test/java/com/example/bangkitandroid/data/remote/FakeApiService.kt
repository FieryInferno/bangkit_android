package com.example.bangkitandroid.data.remote

import com.example.bangkitandroid.data.remote.request.LoginRequest
import com.example.bangkitandroid.data.remote.request.RegisterRequest
import com.example.bangkitandroid.data.remote.response.DiseaseHistoryResponse
import com.example.bangkitandroid.data.remote.response.DiseaseResponse
import com.example.bangkitandroid.data.remote.response.LoginResponse
import com.example.bangkitandroid.data.remote.response.RegisterResponse
import com.example.bangkitandroid.data.remote.response.BlogResponse
import com.example.bangkitandroid.data.remote.response.CommentResponse
import com.example.bangkitandroid.data.remote.response.EditProfileResponse
import com.example.bangkitandroid.data.remote.response.ListBlogResponse
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

    override fun editProfile(
        token: String,
        name: String,
        phoneNumber: String
    ): EditProfileResponse {
        TODO("Not yet implemented")
    }

    override suspend fun login(request: LoginRequest): LoginResponse {
        TODO("Not yet implemented")
    }

    override suspend fun register(request: RegisterRequest): RegisterResponse {
        TODO("Not yet implemented")
    }


    override fun getBlog(id: Int): BlogResponse {
        return BlogResponse(
            success = true,
            message = "success",
            blog = DummyData().getDetailBlog(id)
        )
    }

    override fun getBlogs(page: Int, size: Int): ListBlogResponse {
        return ListBlogResponse(
            success = true,
            message = "success",
            blogs = DummyData().getListBlogs()
        )
    }

    override fun postComment(token: String, dateTime: String, description: String): CommentResponse {
        if(token == ""){
            return CommentResponse(
                success = false,
                message = "invalid token",
                comments = null
            )
        }
        return CommentResponse(
            success = true,
            message = "success",
            comments = DummyData().getDetailBlog(0).comments[0]
        )
    }

    override fun editProfile(token: String, name: String, phoneNumber: String): EditProfileResponse {
        return DummyData().generateEditProfileResponse()
    }
}
