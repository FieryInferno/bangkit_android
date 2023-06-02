package com.example.bangkitandroid.ui.disease

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.bangkitandroid.data.remote.Repository
import com.example.bangkitandroid.domain.entities.Disease
import com.example.bangkitandroid.service.Result
import java.io.File

class DiseaseViewModel(private val repository: Repository) : ViewModel() {
    fun getHistoryDisease(token: String) : LiveData<PagingData<Disease>> = repository.getHistoryDisease(token)
    fun postAnalyzeDisease(token: String, photo: File) : LiveData<Result<Disease>> = repository.postAnalyzeDisease(token, photo)
}