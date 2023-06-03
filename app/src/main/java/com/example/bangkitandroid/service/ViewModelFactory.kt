package com.example.bangkitandroid.service

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bangkitandroid.data.remote.Repository
import com.example.bangkitandroid.ui.home.HomeViewModel
import com.example.bangkitandroid.ui.profile.ProfileViewModel
import com.example.bangkitandroid.ui.authentication.AuthenticationViewModel
import com.example.bangkitandroid.ui.blog.BlogViewModel
import com.example.bangkitandroid.ui.disease.DiseaseViewModel

class ViewModelFactory private constructor(private val repository: Repository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(AuthenticationViewModel::class.java)) {
            AuthenticationViewModel(repository) as T
        } else if(modelClass.isAssignableFrom(HomeViewModel::class.java)){
            HomeViewModel(repository) as T
        }else if(modelClass.isAssignableFrom(DiseaseViewModel::class.java)){
            DiseaseViewModel(repository) as T
        }else if(modelClass.isAssignableFrom(BlogViewModel::class.java)){
            BlogViewModel(repository) as T
        }else if(modelClass.isAssignableFrom(ProfileViewModel::class.java)){
            ProfileViewModel(repository) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
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