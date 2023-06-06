package com.example.bangkitandroid.data.remote.retrofit

import com.example.bangkitandroid.data.remote.request.LoginRequest
import com.example.bangkitandroid.data.remote.request.RegisterRequest
import com.example.bangkitandroid.data.remote.response.DiseaseHistoryResponse
import com.example.bangkitandroid.data.remote.response.DiseaseResponse
import com.example.bangkitandroid.data.remote.response.EditProfileResponse
import retrofit2.Call
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

interface ApiService {
    @GET("blog")
    fun getBlog(
        @Query("id") id: Int,
    ): BlogResponse

    @GET("blogs")
    fun getBlogs(
        @Query("Page") page: Int,
        @Query("Size") size: Int,
    ): ListBlogResponse

    @GET("v1/history")
    suspend fun getDiseases(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("limit") size: Int,
    ): DiseaseHistoryResponse

    @Multipart
    @POST("v1/disease/")
    suspend fun postDisease(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
    ): DiseaseResponse
 
    @FormUrlEncoded
    @POST("auth/edit-profile")
    fun editProfile(
        @Header("Authorization") token: String,
        @Field("name") name: String,
        @Field("phone_number") phoneNumber: String,
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
}
