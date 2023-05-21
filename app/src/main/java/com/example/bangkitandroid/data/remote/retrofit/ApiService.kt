package com.example.bangkitandroid.data.remote.retrofit

import com.example.bangkitandroid.data.remote.response.DiseaseHistoryResponse
import com.example.bangkitandroid.data.remote.response.DiseaseResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
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

}