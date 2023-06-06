package com.example.bangkitandroid.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.bangkitandroid.data.local.TokenPreferences
import com.example.bangkitandroid.data.paging.DiseasePagingSource
import com.example.bangkitandroid.data.remote.request.LoginRequest
import com.example.bangkitandroid.data.remote.response.HomeResponse
import com.example.bangkitandroid.data.remote.response.LoginResult
import com.example.bangkitandroid.data.remote.response.RegisterResult
import com.example.bangkitandroid.data.remote.retrofit.ApiService
import com.example.bangkitandroid.domain.entities.Blog
import com.example.bangkitandroid.domain.entities.Comment
import com.example.bangkitandroid.domain.entities.History
import com.example.bangkitandroid.domain.entities.User
import com.example.bangkitandroid.domain.mapper.toDisease
import com.example.bangkitandroid.service.DummyData
import com.example.bangkitandroid.service.Result
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class Repository (
    private val apiService: ApiService,
    private val tokenPreferences: TokenPreferences
){

    private val blogResult = MediatorLiveData<Result<Blog>>()
    private val commentResult = MediatorLiveData<Result<Comment>>()
    private val loginResult = MediatorLiveData<Result<LoginResult>>()
    private val registerResult = MediatorLiveData<Result<RegisterResult>>()
    private val getUserResult = MediatorLiveData<Result<User>>()
    private val editProfileResult = MediatorLiveData<Result<User>>()
    private var _token = ""


    fun getHome(token: String?): LiveData<Result<HomeResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getHome(_token)
            emit(Result.Success(response))
        } catch (e: Exception) {
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

    fun postAnalyzeDisease(photo: File?) = liveData {
        emit(Result.Loading)
        try {
            val requestImageFile = photo!!.asRequestBody("image/jpg".toMediaType())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "image",
                photo.name,
                requestImageFile
            )

            val response = apiService.postDisease(token = _token, file = imageMultipart)

            emit(Result.Success(response.toDisease()))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getHistoryDisease(): Flow<PagingData<History>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
            ),
            pagingSourceFactory = {
                DiseasePagingSource(apiService, _token)
            }
        ).flow
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
            emit(Result.Error("Nomor Telepon dan kata sandi salah."))
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
            emit(Result.Error("Gagal Daftar"))
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
        _token = "Bearer $token"
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