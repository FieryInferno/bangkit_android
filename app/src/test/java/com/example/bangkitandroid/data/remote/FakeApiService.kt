package com.example.bangkitandroid.data.remote

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
import retrofit2.Call

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
    
    override fun getBlog(id: Int): BlogResponse {
        return BlogResponse(
            image = "",
            description = "",
            id = 0,
            title = "",
            user = 0,
            timestamp = ""
        )
    }

    override fun getBlogs(page: Int, size: Int): ListBlogResponse {
        return ListBlogResponse(
            result = emptyList()
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
}
