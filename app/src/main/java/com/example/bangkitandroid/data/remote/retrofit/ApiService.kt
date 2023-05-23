package com.example.bangkitandroid.data.remote.retrofit
import com.example.bangkitandroid.data.remote.response.DiseaseHistoryResponse
import com.example.bangkitandroid.data.remote.response.ListBlogResponse
import com.example.bangkitandroid.data.remote.response.DiseaseResponse
import com.example.bangkitandroid.data.remote.response.EditProfileResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @GET("history")
    fun getDiseases(
        @Header("Authorization") token: String,
        @Query("Page") page: Int,
        @Query("Size") size: Int,
    ): DiseaseHistoryResponse

    @GET("blog")
    fun getBlogs(
        @Query("Page") page: Int,
        @Query("Size") size: Int,
    ): ListBlogResponse

    @GET("disease")
    fun getDisease(
        @Header("Authorization") token: String,
        @Query("id") id: Int,
    ): DiseaseResponse

    @GET("diseases")
    fun getDiseases(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): DiseaseHistoryResponse

    @Multipart
    @POST("analyze")
    fun postDisease(
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
}

