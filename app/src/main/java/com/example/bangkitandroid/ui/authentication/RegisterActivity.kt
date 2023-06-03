package com.example.bangkitandroid.ui.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import com.example.bangkitandroid.R
import com.example.bangkitandroid.component.NameEditText
import com.example.bangkitandroid.component.PasswordEditText
import com.example.bangkitandroid.component.PhoneNumberEditText
import com.example.bangkitandroid.data.remote.request.RegisterRequest
import com.example.bangkitandroid.databinding.ActivityDiseaseHistoryBinding
import com.example.bangkitandroid.databinding.ActivityLoginBinding
import com.example.bangkitandroid.databinding.ActivityRegisterBinding
import com.example.bangkitandroid.service.Result
import com.example.bangkitandroid.service.ViewModelFactory

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: AuthenticationViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }
    private lateinit var nameEditText: NameEditText
    private lateinit var phoneNumberEditText: PhoneNumberEditText
    private lateinit var passwordEditText: PasswordEditText
    private lateinit var registerButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        setView()
    }

    private fun setView(){
        nameEditText = binding.etNama
        phoneNumberEditText = binding.etNomorTelepon
        passwordEditText = binding.etPassword
        registerButton = binding.btnDaftar

        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                setEnableRegisterButton()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        nameEditText.addTextChangedListener(textWatcher)
        phoneNumberEditText.addTextChangedListener(textWatcher)
        passwordEditText.addTextChangedListener(textWatcher)

        registerButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val phoneNumber = phoneNumberEditText.text.toString()
            val password = passwordEditText.text.toString()

            viewModel.register(name, phoneNumber, password).observe(this) {result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is Result.Success -> {
                            binding.progressBar.visibility = View.GONE
                            val data = result.data
                            //belum selesai tokennya belum di buat di API
                            viewModel.setToken("token", data.name)
                            Log.e("data", data.toString())
                            //set move intent
                        }
                        is Result.Error -> {
                            binding.progressBar.visibility = View.GONE
                            val errorMessage = result.error
                            Toast.makeText(this, "Error: $errorMessage", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        }


        binding.apply {
            btnRegisterBack.setOnClickListener {
                finish()
            }
        }
    }

    private fun setEnableRegisterButton() {
        val name = nameEditText.text
        val phoneNumber = phoneNumberEditText.text
        val password = passwordEditText.text
        binding.btnDaftar.isEnabled =
                nameEditText.error == null &&
                phoneNumberEditText.error == null &&
                passwordEditText.error == null &&
                name != null &&
                phoneNumber != null &&
                password != null &&
                name.toString().isNotEmpty() &&
                phoneNumber.toString().isNotEmpty() &&
                password.toString().isNotEmpty()
    }
}