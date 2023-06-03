package com.example.bangkitandroid.ui.disease

import android.graphics.BitmapFactory
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.bangkitandroid.data.remote.Repository
import com.example.bangkitandroid.domain.entities.Disease
import com.example.bangkitandroid.service.Result
import java.io.File

class DiseaseViewModel(private val repository: Repository) : ViewModel() {

    private val imgFile = MediatorLiveData<File?>(null)

    fun setFile(file: File){
        imgFile.value = file
    }
    fun getFile() : LiveData<File?>{
        return imgFile
    }

    fun deleteFile(){
        imgFile.value = null
    }

    fun getHistoryDisease(token: String) : LiveData<PagingData<Disease>> = repository.getHistoryDisease(token)
    fun postAnalyzeDisease(token: String, photo: File) : LiveData<Result<Disease>> = repository.postAnalyzeDisease(token, photo)
}