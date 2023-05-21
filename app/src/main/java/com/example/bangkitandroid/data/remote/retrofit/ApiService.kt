package com.example.bangkitandroid.data.remote.retrofit

import com.example.bangkitandroid.data.remote.response.LoginResponse
import com.example.bangkitandroid.data.remote.response.RegisterResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("auth/login/")
    fun login(
        @Field("phone_number") phoneNumber: String,
        @Field("password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("auth/login/")
    fun register(
        @Field("name") name: String,
        @Field("phone_number") phoneNumber: String,
        @Field("password") password: String
    ): RegisterResponse
}