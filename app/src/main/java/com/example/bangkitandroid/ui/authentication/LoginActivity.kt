package com.example.bangkitandroid.ui.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bangkitandroid.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private var binding: ActivityLoginBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        supportActionBar?.hide()
        setView()
    }

    private fun setView(){
        binding?.apply {
            btnLoginBack.setOnClickListener {
                finish()
            }
        }
    }
}