package com.example.bangkitandroid.ui.profile

import androidx.lifecycle.ViewModel

class ProfileViewModel(private val repository: Repository) : ViewModel() {
    fun getUser() = repository.getUser()

    fun editUser(name: String, phoneNumber: String) = repository.editProfile(name, phoneNumber)
}