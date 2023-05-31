package com.example.bangkitandroid.data.remote.retrofit
import com.example.bangkitandroid.data.remote.response.DiseaseHistoryResponse
import com.example.bangkitandroid.data.remote.response.DiseaseResponse
import com.example.bangkitandroid.data.remote.response.EditProfileResponse
import retrofit2.Call
import retrofit2.http.*
import com.example.bangkitandroid.data.remote.response.LoginResponse
import com.example.bangkitandroid.data.remote.response.RegisterResponse
import com.example.bangkitandroid.data.remote.response.BlogResponse
import com.example.bangkitandroid.data.remote.response.CommentResponse
import com.example.bangkitandroid.data.remote.response.ListBlogResponse
import okhttp3.MultipartBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

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

    @FormUrlEncoded
    @POST("comment/submit/")
    fun postComment(
        @Header("Authorization") token: String,
        @Field("dateTime") dateTime: String,
        @Field("description") description: String,
    ): CommentResponse
}
