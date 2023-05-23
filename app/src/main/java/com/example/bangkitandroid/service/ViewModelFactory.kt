package com.example.bangkitandroid.service

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bangkitandroid.data.Repository
import com.example.bangkitandroid.ui.home.HomeViewModel
import com.example.bangkitandroid.ui.profile.ProfileViewModel

class ViewModelFactory private constructor(private val repository: Repository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                ProfileViewModel(repository) as T
                HomeViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: ViewModelFactory(Injection.provideRepository(context))
            }.also {
                INSTANCE = it
            }
    }
}