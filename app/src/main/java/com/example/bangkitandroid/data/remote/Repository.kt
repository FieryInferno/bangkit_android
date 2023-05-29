package com.example.bangkitandroid.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.asLiveData
import androidx.paging.PagingData
import com.example.bangkitandroid.data.local.TokenPreferences
import com.example.bangkitandroid.data.remote.request.LoginRequest
import com.example.bangkitandroid.data.remote.request.RegisterRequest
import com.example.bangkitandroid.data.remote.response.LoginResponse
import com.example.bangkitandroid.data.remote.response.LoginResult
import com.example.bangkitandroid.data.remote.response.RegisterResponse
import com.example.bangkitandroid.data.remote.response.RegisterResult
import com.example.bangkitandroid.data.remote.retrofit.ApiService
import com.example.bangkitandroid.domain.entities.Blog
import com.example.bangkitandroid.domain.entities.Comment
import com.example.bangkitandroid.domain.entities.Disease
import com.example.bangkitandroid.service.DummyData
import com.example.bangkitandroid.service.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class Repository (
    private val apiService: ApiService,
    private val tokenPreferences: TokenPreferences
){

    private val diseaseResult = MediatorLiveData<Result<Disease>>()
    private val blogResult = MediatorLiveData<Result<Blog>>()
    private val commentResult = MediatorLiveData<Result<Comment>>()
    private val loginResult = MediatorLiveData<Result<LoginResult>>()
    private val registerResult = MediatorLiveData<Result<RegisterResult>>()

    fun getDiseaseDetail(token: String, id: Int) : LiveData<Result<Disease>>{
        diseaseResult.value = Result.Success(DummyData().getDetailDiseaseDummy(0))
        return diseaseResult
    }

    fun postAnalyzeDisease(token: String, photo: File) : LiveData<Result<Disease>>{
        diseaseResult.value = Result.Success(DummyData().getDetailDiseaseDummy(0))
        return diseaseResult
    }

    fun getHistoryDisease(token: String): LiveData<PagingData<Disease>> {
        val pagingDataResult = MediatorLiveData<PagingData<Disease>>()
        pagingDataResult.value = PagingData.from(DummyData().getHistoryDiseasesDummy())
        return pagingDataResult
    }
    
    fun getBlogDetail(id: Int) : LiveData<Result<Blog>> {
        blogResult.value = Result.Success(DummyData().getDetailBlogDummy(id))
        return blogResult
    }

    fun getListBlog() : LiveData<PagingData<Blog>> {
        val pagingDataResult = MediatorLiveData<PagingData<Blog>>()
        pagingDataResult.value = PagingData.from(DummyData().getListBlogsDummy())
        return pagingDataResult
    }

    fun getListComment() : LiveData<PagingData<Comment>> {
        val pagingDataResult = MediatorLiveData<PagingData<Comment>>()
        val listComment = DummyData().getDetailBlogDummy(0).comments
        pagingDataResult.value = PagingData.from(listComment)
        return pagingDataResult
    }

    fun postComment(token: String, dateTime: String, description: String) : LiveData<Result<Comment>>{
        commentResult.value = Result.Success(DummyData().getDetailBlogDummy(0).comments[0])
        return commentResult
    }

    fun login(phoneNumber: String, password: String) : LiveData<Result<LoginResult>> {
        loginResult.value = Result.Loading
        val body = LoginRequest(phoneNumber, password)
        val client = apiService.login(body)

        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody?.data != null){
                        loginResult.value = Result.Success(responseBody.data)
                    } else {
                        loginResult.value = Result.Error(responseBody?.message.toString())
                    }
                } else{
                    loginResult.value = Result.Error(response.message())
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                loginResult.value = Result.Error(t.message.toString())
                Log.e(AuthenticationTAG, "onFailure: ${t.message}")
            }

        })
        return loginResult
    }

    fun register(name: String, phoneNumber: String, password: String) : LiveData<Result<RegisterResult>>{
        registerResult.value = Result.Loading
        val body = RegisterRequest(name, phoneNumber, password)
        val client = apiService.register(body)

        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody?.data != null){
                        registerResult.value = Result.Success(responseBody.data)
                    } else {
                        registerResult.value = Result.Error(responseBody?.message.toString())
                    }
                } else {
                    registerResult.value = Result.Error(response.message())
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                registerResult.value = Result.Error(t.message.toString())
                Log.e(AuthenticationTAG, "onFailure: ${t.message}")
            }

        })
        return registerResult
    }

    suspend fun logout(){
        tokenPreferences.deleteToken()
    }

    fun getToken(): LiveData<String> {
        return tokenPreferences.getToken().asLiveData()
    }

    fun getSessionId(): LiveData<String> {
        return tokenPreferences.getSession().asLiveData()
    }

    suspend fun setToken(token: String, sessionId: String){
        tokenPreferences.saveToken(token, sessionId)
    }

    companion object {
        const val DiseaseTAG = "Disease"
        private const val AuthenticationTAG = "Authentication"
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            apiService: ApiService,
            tokenPreferences: TokenPreferences
        ): Repository = instance ?: synchronized(this){
            instance ?: Repository(apiService, tokenPreferences)
        }.also {
            instance = it
        }
    }
}