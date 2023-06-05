package com.example.bangkitandroid.data.remote

import com.example.bangkitandroid.data.remote.model.HistoryModel
import com.example.bangkitandroid.data.remote.response.*
import com.example.bangkitandroid.data.remote.retrofit.ApiService
import com.example.bangkitandroid.service.DummyData
import okhttp3.MultipartBody

class FakeApiService : ApiService {

    override suspend fun getDiseases(token: String, page: Int, size: Int): DiseaseHistoryResponse {
        return DiseaseHistoryResponse(
            result = DummyData().getHistoryModels(),
            meta = Meta(
                totalItem = "",
                hasPrevious = false,
                limit = "",
                hasNext = false,
                totalPages = "",
                currentPage = ""
            )
        )
    }

    override suspend fun postDisease(token: String, file: MultipartBody.Part): DiseaseResponse {
        if(token == "Bearer "){
            throw Exception("invalid token")
        }
        return DiseaseResponse(
            success = true,
            statusCode = 200,
            data = HistoryModel(
                image = "image.jpg",
                disease = DummyData().getDetailDiseaseModel(0),
                id = 0,
                user = 0,
                timestamp = ""
            )
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
            comments = null
        )
    }

    override suspend fun getHome(token: String?): HomeResponse {
        return HomeResponse(
            isAuthenticated = true,
            history = DummyData().getHistoryModels(),
            blogs = DummyData().getListBlogModels(),
        )
    }

    override fun editProfile(token: String, name: String, phoneNumber: String): EditProfileResponse {
        return DummyData().generateEditProfileResponse()
    }
}
