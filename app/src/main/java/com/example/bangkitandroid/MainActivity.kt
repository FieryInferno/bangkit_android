package com.example.bangkitandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.bangkitandroid.service.ViewModelFactory
import com.example.bangkitandroid.ui.home.HomeActivityLogged
import com.example.bangkitandroid.ui.home.HomeActivityNotLogged
import com.example.bangkitandroid.ui.home.HomeViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel : HomeViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setView()
    }

    private fun setView(){
        viewModel.getSessionId().observe(this) { session ->
            if (session.isEmpty()) {
                startActivity(Intent(this, HomeActivityNotLogged::class.java))
                finish()
            } else {
                viewModel.getToken().observe(this) { token ->
                    if (token.isNotEmpty()) {
                        viewModel.setToken(token, session)
                        startActivity(Intent(this, HomeActivityLogged::class.java))
                        finish()
                    }
                }
            }
        }

        setContent {
            Loading()
        }
    }

    @Composable
    fun Loading() {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color.Green)
        }
    }
}