package com.example.bangkitandroid.ui.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import com.example.bangkitandroid.component.PasswordEditText
import com.example.bangkitandroid.component.PhoneNumberEditText
import com.example.bangkitandroid.databinding.ActivityLoginBinding
import com.example.bangkitandroid.service.Result
import com.example.bangkitandroid.service.ViewModelFactory
import com.example.bangkitandroid.ui.home.HomeActivityLogged

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: AuthenticationViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }
    private lateinit var phoneNumberEditText: PhoneNumberEditText
    private lateinit var passwordEditText: PasswordEditText
    private lateinit var loginButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        setView()
    }

    private fun setView(){
        phoneNumberEditText = binding.etNomorTelepon
        passwordEditText = binding.etPassword
        loginButton = binding.btnMasuk

        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                setEnableLoginButton()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        phoneNumberEditText.addTextChangedListener(textWatcher)
        passwordEditText.addTextChangedListener(textWatcher)

        loginButton.setOnClickListener {
            val phoneNumber = phoneNumberEditText.text.toString()
            val password = passwordEditText.text.toString()
            viewModel.login(phoneNumber, password).observe(this) {result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is Result.Success -> {
                            binding.progressBar.visibility = View.GONE
                            val data = result.data
                            viewModel.setToken(data.token, data.user.name)
                            viewModel.getToken().observe(this) {token ->
                                Log.e("data", token)
                            }
                            //set move intent
                            startActivity(Intent(this@LoginActivity, HomeActivityLogged::class.java))
                            finish()
                        }
                        is Result.Error -> {
                            binding.progressBar.visibility = View.GONE
                            val errorMessage = result.error
                            Toast.makeText(this@LoginActivity, errorMessage, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        binding.apply {
            btnLoginBack.setOnClickListener {
                finish()
            }
        }
    }

    private fun setEnableLoginButton() {
        val phoneNumber = phoneNumberEditText.text
        val password = passwordEditText.text
        binding.btnMasuk.isEnabled =
                phoneNumberEditText.error == null &&
                passwordEditText.error == null &&
                phoneNumber != null &&
                password != null &&
                phoneNumber.toString().isNotEmpty() &&
                password.toString().isNotEmpty()
    }
}