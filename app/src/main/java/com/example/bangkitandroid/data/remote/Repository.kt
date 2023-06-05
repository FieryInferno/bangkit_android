package com.example.bangkitandroid.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.liveData
import androidx.paging.PagingData
import com.example.bangkitandroid.data.local.TokenPreferences
import com.example.bangkitandroid.data.remote.request.LoginRequest
import com.example.bangkitandroid.data.remote.request.RegisterRequest
import com.example.bangkitandroid.data.remote.response.LoginResponse
import com.example.bangkitandroid.data.remote.response.LoginResult
import com.example.bangkitandroid.data.remote.response.RegisterResponse
import com.example.bangkitandroid.data.remote.response.RegisterResult
import com.example.bangkitandroid.data.remote.response.HomeResponse
import com.example.bangkitandroid.data.remote.retrofit.ApiService
import com.example.bangkitandroid.domain.entities.Blog
import com.example.bangkitandroid.domain.entities.Comment
import com.example.bangkitandroid.domain.entities.Disease
import com.example.bangkitandroid.domain.entities.User
import com.example.bangkitandroid.service.DummyData
import com.example.bangkitandroid.service.Result
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.lang.Exception

class Repository (
    private val apiService: ApiService,
    private val tokenPreferences: TokenPreferences
){

    private val diseaseResult = MediatorLiveData<Result<Disease>>()
    private val blogResult = MediatorLiveData<Result<Blog>>()
    private val commentResult = MediatorLiveData<Result<Comment>>()
    private val loginResult = MediatorLiveData<Result<LoginResult>>()
    private val registerResult = MediatorLiveData<Result<RegisterResult>>()
    private val getUserResult = MediatorLiveData<Result<User>>()
    private val editProfileResult = MediatorLiveData<Result<User>>()


    fun getHome(token: String?): LiveData<Result<HomeResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getHome(token)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d(HomeTAG, "getHome: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getUser(): LiveData<Result<User>> {
        getUserResult.value = Result.Success(DummyData().getUserDummy(1))
        return getUserResult
    }

    fun editProfile(name: String, phoneNumber: String): LiveData<Result<User>> {
        editProfileResult.value = Result.Success(DummyData().getUserDummy(1))
        return editProfileResult
    }

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

//    fun getListComment() : LiveData<PagingData<Comment>> {
//        val pagingDataResult = MediatorLiveData<PagingData<Comment>>()
//        val listComment = DummyData().getDetailBlogDummy(0).comments
//        pagingDataResult.value = PagingData.from(listComment)
//        return pagingDataResult
//    }
//
//    fun postComment(token: String, dateTime: String, description: String) : LiveData<Result<Comment>>{
//        commentResult.value = Result.Success(DummyData().getDetailBlogDummy(0).comments[0])
//        return commentResult
//    }

    fun login(phoneNumber: String, password: String) : LiveData<Result<LoginResult>> = liveData {
        emit(Result.Loading)
        try {
            val loginRequest = LoginRequest(phoneNumber, password)
            val response = apiService.login(loginRequest)
            val login = response.data
            emit(Result.Success(login))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun register(name: RequestBody, phoneNumber: RequestBody, password: RequestBody, image: MultipartBody.Part) : LiveData<Result<RegisterResult>> = liveData{
        emit(Result.Loading)
        try {
//            val registerRequest = RegisterRequest(name, phoneNumber, password)
            val response = apiService.register(name, phoneNumber, password, image)
            Log.e("repo", "jalan")
            val register = response.data
            emit(Result.Success(register))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
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
        const val HomeTAG = "Home"
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