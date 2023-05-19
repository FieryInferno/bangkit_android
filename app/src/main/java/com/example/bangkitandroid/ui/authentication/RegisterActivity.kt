package com.example.bangkitandroid.ui.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bangkitandroid.R
import com.example.bangkitandroid.databinding.ActivityDiseaseHistoryBinding
import com.example.bangkitandroid.databinding.ActivityLoginBinding
import com.example.bangkitandroid.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private var binding: ActivityRegisterBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        supportActionBar?.hide()
        setView()
    }

    private fun setView(){
        binding?.apply {
            btnRegisterBack.setOnClickListener {
                finish()
            }
        }
    }
}