package com.example.bangkitandroid.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.bangkitandroid.data.remote.Repository
import java.io.File

class HomeViewModel(private val repository: Repository): ViewModel() {
    private val imgFile = MediatorLiveData<File?>(null)

    fun setFile(file: File){
        imgFile.value = file
    }
    fun getFile() : LiveData<File?> {
        return imgFile
    }
    fun getHome(token: String) = repository.getHome(token)

    fun getToken() = repository.getToken()

    fun getSessionId() = repository.getSessionId()
}