package com.example.bangkitandroid.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bangkitandroid.data.remote.Repository
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: Repository) : ViewModel() {
    fun getUser() = repository.getUser()

    fun editUser(name: String, phoneNumber: String) = repository.editProfile(name, phoneNumber)

    fun logout(){
        viewModelScope.launch {
            repository.logout()
        }
    }
}