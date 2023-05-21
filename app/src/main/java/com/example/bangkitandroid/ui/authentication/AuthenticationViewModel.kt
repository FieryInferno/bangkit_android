package com.example.bangkitandroid.ui.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.bangkitandroid.data.Repository
import com.example.bangkitandroid.domain.entities.User
import com.example.bangkitandroid.service.Result

class AuthenticationViewModel(private val repository: Repository) : ViewModel() {
    fun login(phoneNumber: String, password: String) = repository.login(phoneNumber, password)

    fun register(name: String, phoneNumber: String, password: String) = repository.register(name, phoneNumber, password)
}