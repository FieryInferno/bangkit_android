package com.example.bangkitandroid.ui.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bangkitandroid.data.remote.Repository
import kotlinx.coroutines.launch

class AuthenticationViewModel(private val repository: Repository) : ViewModel() {
    fun login(phoneNumber: String, password: String) = repository.login(phoneNumber, password)

    fun register(name: String, phoneNumber: String, password: String) = repository.register(name, phoneNumber, password)

    fun setToken(token: String, sessionId: String){
        viewModelScope.launch {
            repository.setToken(token, sessionId)
        }
    }
    fun logout(){
        viewModelScope.launch {
            repository.logout()
        }
    }
    fun getToken() = repository.getToken()
    fun getSessionId() = repository.getSessionId()
}