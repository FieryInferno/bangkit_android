package com.example.bangkitandroid.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.bangkitandroid.data.BlogPagingSource
import com.example.bangkitandroid.data.local.TokenPreferences
import com.example.bangkitandroid.data.paging.DiseasePagingSource
import com.example.bangkitandroid.data.remote.model.BlogModel
import com.example.bangkitandroid.data.remote.request.LoginRequest
import com.example.bangkitandroid.data.remote.response.BlogResponse
import com.example.bangkitandroid.data.remote.response.HomeResponse
import com.example.bangkitandroid.data.remote.response.LoginResult
import com.example.bangkitandroid.data.remote.response.RegisterResult
import com.example.bangkitandroid.data.remote.retrofit.ApiService
import com.example.bangkitandroid.domain.entities.Blog
import com.example.bangkitandroid.domain.entities.Comment
import com.example.bangkitandroid.domain.entities.History
import com.example.bangkitandroid.domain.entities.User
import com.example.bangkitandroid.domain.mapper.toDisease
import com.example.bangkitandroid.domain.mapper.toUser
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
    private val tokenPreferences: TokenPreferences?
){
    private var _token = ""

    fun getHome(): LiveData<Result<HomeResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getHome(_token)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getUser(): LiveData<Result<User>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getUser(_token)
            val user = response.user.toUser()
            emit(Result.Success(user))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun editProfile(name: RequestBody, phoneNumber: RequestBody, image: MultipartBody.Part?): LiveData<Result<User>> = liveData {
        emit(Result.Loading)
        try{
            val response = apiService.editProfile(_token, name, phoneNumber, image)
            val user = response.user.toUser()
            emit(Result.Success(user))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
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

    fun getBlogDetail(id: Int): LiveData<Result<BlogResponse>> = liveData {
        Log.e("repository", "JALAN")
        emit(Result.Loading)
        try {
            val response = apiService.getBlog(id)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getListBlogs(): LiveData<PagingData<BlogModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            pagingSourceFactory = {
                BlogPagingSource(apiService)
            }
        ).liveData
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

    fun register(name: RequestBody, phoneNumber: RequestBody, password: RequestBody, image: MultipartBody.Part) : LiveData<Result<RegisterResult>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.register(name, phoneNumber, password, image)
            val register = response.data
            emit(Result.Success(register))
        } catch (e: Exception) {
            emit(Result.Error("Gagal Daftar"))
        }
    }
//    fun getListComment() : LiveData<PagingData<Comment>> {
//        return Pager(
//            config = PagingConfig(
//                pageSize = 5
//            ),
//            pagingSourceFactory = {
//                CommentPagingSource(apiService)
//            }
//        ).liveData
//    }

    suspend fun logout(){
        tokenPreferences!!.deleteToken()
    }

    fun getToken(): LiveData<String> {
        return tokenPreferences!!.getToken().asLiveData()
    }

    fun getSessionId(): LiveData<String> {
        return tokenPreferences!!.getSession().asLiveData()
    }

    suspend fun setToken(token: String, sessionId: String){
        _token = "Bearer $token"
        tokenPreferences!!.saveToken(token, sessionId)
    }

    companion object {
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