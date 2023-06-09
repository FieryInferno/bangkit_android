package com.example.bangkitandroid.ui.disease

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.bangkitandroid.data.remote.Repository
import com.example.bangkitandroid.domain.entities.Disease
import com.example.bangkitandroid.domain.entities.History
import com.example.bangkitandroid.service.Result
import com.example.bangkitandroid.service.reduceFileImage
import kotlinx.coroutines.flow.Flow
import java.io.File

class DiseaseViewModel(private val repository: Repository) : ViewModel() {

    private val imgFile = MediatorLiveData<File?>(null)

    fun setFile(file: File) {
        imgFile.value = file
    }

    fun getFile(): LiveData<File?> {
        return imgFile
    }

    fun getHistoryDisease(): Flow<PagingData<History>> =
        repository.getHistoryDisease().cachedIn(viewModelScope)

    fun postAnalyzeDisease(): LiveData<Result<Disease>> =
        repository.postAnalyzeDisease(imgFile.value)
}