package com.example.bangkitandroid.service

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bangkitandroid.data.remote.Repository
import com.example.bangkitandroid.domain.entities.Blog
import com.example.bangkitandroid.ui.home.HomeViewModel
import com.example.bangkitandroid.ui.profile.ProfileViewModel
import com.example.bangkitandroid.ui.authentication.AuthenticationViewModel
import com.example.bangkitandroid.ui.blog.BlogViewModel

class ViewModelFactory private constructor(private val repository: Repository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                AuthenticationViewModel(repository) as T
                ProfileViewModel(repository) as T
                HomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(BlogViewModel::class.java) -> {
                BlogViewModel(repository) as T
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