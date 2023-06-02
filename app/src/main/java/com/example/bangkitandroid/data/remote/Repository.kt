package com.example.bangkitandroid.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.bangkitandroid.data.paging.DiseasePagingSource
import com.example.bangkitandroid.data.remote.response.HomeResponse
import com.example.bangkitandroid.data.remote.retrofit.ApiService
import com.example.bangkitandroid.domain.entities.Blog
import com.example.bangkitandroid.domain.entities.Disease
import com.example.bangkitandroid.domain.entities.User
import com.example.bangkitandroid.domain.mapper.toDisease
import com.example.bangkitandroid.service.DummyData
import com.example.bangkitandroid.service.Result
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.lang.Exception

class Repository (
    private val apiService: ApiService,
){

    private val blogResult = MediatorLiveData<Result<Blog>>()
    private val getUserResult = MediatorLiveData<Result<User>>()
    private val editProfileResult = MediatorLiveData<Result<User>>()
    private val loginResult = MediatorLiveData<Result<User>>()
    private val registerResult = MediatorLiveData<Result<User>>()

    fun login(phoneNumber: String, password: String): LiveData<Result<User>> {
        loginResult.value = Result.Success(DummyData().getUserDummy(1))
        return loginResult
    }

    fun register(name: String, phoneNumber: String, password: String): LiveData<Result<User>> {
        registerResult.value = Result.Success(DummyData().getUserDummy(1))
        return registerResult
    }

    fun getHome(token: String?): LiveData<Result<HomeResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getHome(token)
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

    fun postAnalyzeDisease(token: String, photo: File) = liveData {
        emit(Result.Loading)
        try {
            val requestImageFile = photo.asRequestBody("image/jpg".toMediaType())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "photo",
                photo.name,
                requestImageFile
            )
            val response = apiService.postDisease(token = "Bearer $token", file = imageMultipart)

            emit(Result.Success(response.toDisease()))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getHistoryDisease(token: String): LiveData<PagingData<Disease>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                DiseasePagingSource(apiService, token)
            }
        ).liveData
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

//    fun postComment(token: String, dateTime: String, description: String) : LiveData<Result<Comment>>{
//        commentResult.value = Result.Success(DummyData().getDetailBlogDummy(0).comments[0])
//        return commentResult
//    }

    companion object {
        const val HomeTAG = "Home"
        const val DiseaseTAG = "Disease"
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            apiService: ApiService,
        ): Repository = instance ?: synchronized(this){
            instance ?: Repository(apiService)
        }.also {
            instance = it
        }
    }
}