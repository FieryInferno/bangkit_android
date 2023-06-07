package com.example.bangkitandroid.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bangkitandroid.data.remote.Repository
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ProfileViewModel(private val repository: Repository) : ViewModel() {
    fun getUser() = repository.getUser()
    fun editProfile(name: RequestBody, phoneNumber: RequestBody, image: MultipartBody.Part?) = repository.editProfile(name, phoneNumber, image)

    fun logout(){
        viewModelScope.launch {
            repository.logout()
        }
    }
}