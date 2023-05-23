package com.example.bangkitandroid.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.paging.PagingData
import com.example.bangkitandroid.data.remote.retrofit.ApiService
import com.example.bangkitandroid.domain.entities.Disease
import com.example.bangkitandroid.service.DummyData
import java.io.File

class Repository (
    private val apiService: ApiService,
){

    private val diseaseResult = MediatorLiveData<Result<Disease>>()

    fun getDiseaseDetail(token: String, id: Int) : LiveData<Result<Disease>>{
        diseaseResult.value = Result.Success(DummyData().getDetailDiseaseDummy())
        return diseaseResult
    }

    fun postAnalyzeDisease(token: String, photo: File) : LiveData<Result<Disease>>{
        diseaseResult.value = Result.Success(DummyData().getDetailDiseaseDummy())
        return diseaseResult
    }

    fun getHistoryDisease(token: String): LiveData<PagingData<Disease>> {
        val pagingDataResult = MediatorLiveData<PagingData<Disease>>()
        pagingDataResult.value = PagingData.from(DummyData().getHistoryDiseasesDummy())
        return pagingDataResult
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