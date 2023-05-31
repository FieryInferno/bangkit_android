package com.example.bangkitandroid.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.bangkitandroid.data.BlogPagingSource
import com.example.bangkitandroid.data.CommentPagingSource
import com.example.bangkitandroid.data.remote.response.BlogResponse
import com.example.bangkitandroid.data.remote.retrofit.ApiService
import com.example.bangkitandroid.domain.entities.Blog
import com.example.bangkitandroid.domain.entities.Comment
import com.example.bangkitandroid.domain.entities.Disease
import com.example.bangkitandroid.domain.entities.User
import com.example.bangkitandroid.service.DummyData
import com.example.bangkitandroid.service.Result
import java.io.File

class Repository (
    private val apiService: ApiService,
){

    private val diseaseResult = MediatorLiveData<Result<Disease>>()
    private val blogResult = MediatorLiveData<Result<BlogResponse>>()
    private val commentResult = MediatorLiveData<Result<Comment>>()
    private val historyResult = MediatorLiveData<Result<List<Disease>>>()
    private val listBlogResult = MediatorLiveData<Result<List<Blog>>>()
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

    fun getHistory(token: String): LiveData<Result<List<Disease>>> {
        historyResult.value = Result.Success(DummyData().getHistoryDiseasesDummy())
        return historyResult
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

    fun getListBlogs(): LiveData<PagingData<BlogResponse>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                BlogPagingSource(apiService)
            }
        ).liveData
    }


    fun getListComment() : LiveData<PagingData<Comment>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                CommentPagingSource(apiService)
            }
        ).liveData
    }

    fun postComment(token: String, dateTime: String, description: String) : LiveData<Result<Comment>>{
        commentResult.value = Result.Success(DummyData().getDetailBlogDummy(0).comments[0])
        return commentResult
    }

    companion object {
        const val BlogTAG = "Blog"
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