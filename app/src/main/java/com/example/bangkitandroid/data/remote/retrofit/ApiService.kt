package com.example.bangkitandroid.data.remote.retrofit

import com.example.bangkitandroid.data.remote.request.LoginRequest
import com.example.bangkitandroid.data.remote.response.DiseaseHistoryResponse
import com.example.bangkitandroid.data.remote.response.DiseaseResponse
import com.example.bangkitandroid.data.remote.response.EditProfileResponse
import retrofit2.http.*
import com.example.bangkitandroid.data.remote.response.LoginResponse
import com.example.bangkitandroid.data.remote.response.RegisterResponse
import com.example.bangkitandroid.data.remote.response.BlogResponse
import com.example.bangkitandroid.data.remote.response.CommentResponse
import com.example.bangkitandroid.data.remote.response.HomeResponse
import com.example.bangkitandroid.data.remote.response.ListBlogResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import com.example.bangkitandroid.data.remote.response.UserResponse

interface ApiService {
    @GET("v1/blog/{id}/")
    suspend fun getBlog(
        @Path("id") id: Int
    ): BlogResponse

    @GET("v1/blog")
    suspend fun getListBlogs(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): ListBlogResponse

    @GET("v1/history")
    suspend fun getDiseases(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): DiseaseHistoryResponse

    @Multipart
    @POST("v1/disease/")
    suspend fun postDisease(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
    ): DiseaseResponse
 
    @Multipart
    @PUT("v1/auth/edit-profile/")
    suspend fun editProfile(
        @Header("Authorization") token: String,
        @Part("name") name: RequestBody,
        @Part("phone_number") phoneNumber: RequestBody,
        @Part file: MultipartBody.Part?,
    ): EditProfileResponse

    @POST("v1/auth/login/")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @Multipart
    @POST("v1/auth/register/")
    suspend fun register(
        @Part ("name") name: RequestBody,
        @Part ("phone_number") phoneNumber: RequestBody,
        @Part ("password") password: RequestBody,
        @Part file: MultipartBody.Part
    ): RegisterResponse

    @FormUrlEncoded
    @POST("comment/submit/")
    fun postComment(
        @Header("Authorization") token: String,
        @Field("dateTime") dateTime: String,
        @Field("description") description: String,
    ): CommentResponse

    @GET("v1/home")
    suspend fun getHome(
        @Header("Authorization") token: String?,
    ): HomeResponse

    @GET("v1/auth/user/")
    suspend fun getUser(
        @Header("Authorization") token: String,
    ): UserResponse
}
