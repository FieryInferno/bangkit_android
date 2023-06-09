package com.example.bangkitandroid.data.remote

import com.example.bangkitandroid.data.remote.model.CommentModel
import com.example.bangkitandroid.data.remote.model.HistoryModel
import com.example.bangkitandroid.data.remote.model.MetaModel
import com.example.bangkitandroid.data.remote.model.UserModel
import com.example.bangkitandroid.data.remote.response.*
import com.example.bangkitandroid.data.remote.request.LoginRequest
import com.example.bangkitandroid.data.remote.response.DiseaseHistoryResponse
import com.example.bangkitandroid.data.remote.response.DiseaseResponse
import com.example.bangkitandroid.data.remote.response.LoginResponse
import com.example.bangkitandroid.data.remote.response.RegisterResponse
import com.example.bangkitandroid.data.remote.response.BlogResponse
import com.example.bangkitandroid.data.remote.response.CommentResponse
import com.example.bangkitandroid.data.remote.response.EditProfileResponse
import com.example.bangkitandroid.data.remote.response.ListBlogResponse
import com.example.bangkitandroid.data.remote.retrofit.ApiService
import com.example.bangkitandroid.domain.entities.Comment
import com.example.bangkitandroid.service.DummyData
import com.example.bangkitandroid.service.Result
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Header
import retrofit2.http.Part

class FakeApiService : ApiService {
    override suspend fun getListBlogs(page: Int, limit: Int): ListBlogResponse {
        return ListBlogResponse(
            result = DummyData().getListBlogModels(),
            meta = MetaModel(
                totalItem = "",
                hasPrevious = false,
                limit = "",
                hasNext = false,
                totalPages = "",
                currentPage = ""
            )
        )
    }

    override suspend fun getDiseases(token: String, page: Int, size: Int): DiseaseHistoryResponse {
        return DiseaseHistoryResponse(
            result = DummyData().getHistoryModels(),
            meta = MetaModel(
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
        return LoginResponse(
            success = true,
            message = "Successfully return",
            data = LoginResult(
                token = "token",
                refreshToken = "token",
                user = DataUser(
                    name = "",
                    phoneNumber = "",
                    email = null
                )
            )
        )
    }

    override suspend fun register(
        name: RequestBody,
        phoneNumber: RequestBody,
        password: RequestBody,
        file: MultipartBody.Part?
    ): RegisterResponse {
        return RegisterResponse(
            success = true,
            message = "Successfully return",
            data = RegisterResult(
                password = "",
                isSuperuser = false,
                isActive = true,
                isStaff = false,
                userPermissions = emptyList(),
                lastLogin = "",
                name = "",
                groups = emptyList(),
                phoneNumber = "",
                id = 0,
                email = "",
                image = ""
            )
        )
    }

    override suspend fun getBlog(id: Int): BlogResponse {
        return BlogResponse(
            image = "https://cdn.britannica.com/89/126689-004-D622CD2F/Potato-leaf-blight.jpg",
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            id = 0,
            title = "Judul Blog 0",
            user = UserModel(
                name = "Penulis",
                phoneNumber = "081234567890",
                email = null,
                image = "https://cdn.britannica.com/89/126689-004-D622CD2F/Potato-leaf-blight.jpg"
            ),
            timestamp = "4 Mei 2023 9:00"
        )
    }

    override suspend fun postComment(
        @Header(value = "Authorization") token: String,
        @Part(value = "message") dateTime: String,
        @Part(value = "id_blog") id_blog: Int
    ): PostCommentResponse {
        return PostCommentResponse(
            success = true,
            message = "Successfully return",
            data = CommentModel(
                id = 1,
                blogId = 0,
                message = "Comment 1",
                user = UserModel(
                    name = "Penulis",
                    phoneNumber = "081234567890",
                    email = null,
                    image = ""
                ),
                timestamp = "4 Mei 2023 9:00"
            )
        )
    }

    override suspend fun getComment(id: Int, page: Int, size: Int): GetCommentResponse {
        return GetCommentResponse(
            result = DummyData().getComment(),
            meta = MetaModel(
                totalItem = "",
                hasPrevious = false,
                limit = "",
                hasNext = false,
                totalPages = "",
                currentPage = ""
            )
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
