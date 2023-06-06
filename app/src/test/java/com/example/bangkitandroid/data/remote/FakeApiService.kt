package com.example.bangkitandroid.data.remote

import com.example.bangkitandroid.data.remote.model.HistoryModel
import com.example.bangkitandroid.data.remote.model.UserModel
import com.example.bangkitandroid.data.remote.request.LoginRequest
import com.example.bangkitandroid.data.remote.response.*
import com.example.bangkitandroid.data.remote.retrofit.ApiService
import com.example.bangkitandroid.service.DummyData
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Header
import retrofit2.http.Part

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

    override suspend fun login(request: LoginRequest): LoginResponse {
        TODO("Not yet implemented")
    }

    override suspend fun register(
        name: RequestBody,
        phoneNumber: RequestBody,
        password: RequestBody,
        file: MultipartBody.Part
    ): RegisterResponse {
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

    override suspend fun getUser(token: String): UserResponse {
        return UserResponse(
            success = true,
            message = "Successfully return",
            user = UserModel(
                name = "user 0",
                phoneNumber = "123456789",
                email = null,
                image = "https://cdn.britannica.com/89/126689-004-D622CD2F/Potato-leaf-blight.jpg"
            )
        )
    }

    override suspend fun editProfile(
        @Header(value = "Authorization") token: String,
        @Part(value = "name") name: RequestBody,
        @Part(value = "phone_number") phoneNumber: RequestBody,
        @Part file: MultipartBody.Part?
    ): EditProfileResponse {
        return EditProfileResponse(
            success = true,
            message = "Successfully return",
            user = UserModel(
                name = "user 0",
                phoneNumber = "123456789",
                email = null,
                image = "https://cdn.britannica.com/89/126689-004-D622CD2F/Potato-leaf-blight.jpg"
            )
        )
    }
}
