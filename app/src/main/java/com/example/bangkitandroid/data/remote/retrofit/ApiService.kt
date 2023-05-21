package com.example.bangkitandroid.data.remote.retrofit

import com.example.bangkitandroid.data.remote.response.BlogResponse
import com.example.bangkitandroid.data.remote.response.ListBlogResponse
import retrofit2.http.GET
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

}