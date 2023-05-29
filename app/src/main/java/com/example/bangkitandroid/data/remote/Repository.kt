package com.example.bangkitandroid.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.paging.PagingData
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
    private val commentResult = MediatorLiveData<Result<Comment>>()
    private val historyResult = MediatorLiveData<Result<List<Disease>>>()
    private val blogResultHome = MediatorLiveData<Result<List<Blog>>>()
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

    fun getHistory(token: String): LiveData<Result<List<Disease>>> {
        historyResult.value = Result.Success(DummyData().getHistoryDiseasesDummy())
        return historyResult
    }

    fun getListBlogHome(): LiveData<Result<List<Blog>>> {
        blogResultHome.value = Result.Success(DummyData().getListBlogsDummy())
        return blogResultHome
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
        diseaseResult.value = Result.Success(DummyData().getDetailDiseaseDummy(id))
        return diseaseResult
    }

    fun postAnalyzeDisease(token: String, photo: File) : LiveData<Result<Disease>>{
        diseaseResult.value = Result.Success(DummyData().getDetailDiseaseDummy(1))
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

    companion object {
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