package com.example.bangkitandroid.ui.disease

import androidx.lifecycle.ViewModel
import com.example.bangkitandroid.data.remote.Repository
import java.io.File

class DiseaseViewModel(private val repository: Repository) : ViewModel() {
    fun getHistoryDisease(token: String) = repository.getHistoryDisease(token)
    fun getDiseaseDetail(token: String, id: Int) = repository.getDiseaseDetail(token, id)
    fun postAnalyzeDisease(token: String, photo: File) = repository.postAnalyzeDisease(token, photo)
}