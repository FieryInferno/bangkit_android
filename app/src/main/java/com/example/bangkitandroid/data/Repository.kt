package com.example.bangkitandroid.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.bangkitandroid.data.remote.retrofit.ApiService
import com.example.bangkitandroid.domain.entities.Blog
import com.example.bangkitandroid.domain.entities.Disease
import com.example.bangkitandroid.service.DummyData
import com.example.bangkitandroid.service.Result

class Repository (
    private val apiService: ApiService,
) {
    private val historyResult = MediatorLiveData<Result<List<Disease>>>()
    private val blogResult = MediatorLiveData<Result<List<Blog>>>()

    fun getHistory(token: String): LiveData<Result<List<Disease>>> {
        historyResult.value = Result.Success(DummyData().getHistoryDiseasesDummy())
        return historyResult
    }

    fun getListBlog(): LiveData<Result<List<Blog>>> {
        blogResult.value = Result.Success(DummyData().getListBlogsDummy())
        return blogResult
    }

    companion object {
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