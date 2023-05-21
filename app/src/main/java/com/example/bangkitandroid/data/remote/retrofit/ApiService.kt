package com.example.bangkitandroid.data.remote.retrofit

import com.example.bangkitandroid.data.remote.response.DiseaseHistoryResponse
import com.example.bangkitandroid.data.remote.response.ListBlogResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {
    @GET("diseases")
    fun getDiseases(
        @Header("Authorization") token: String,
        @Query("Page") page: Int,
        @Query("Size") size: Int,
    ): DiseaseHistoryResponse

    @GET("blogs")
    fun getBlogs(
        @Query("Page") page: Int,
        @Query("Size") size: Int,
    ): ListBlogResponse
}