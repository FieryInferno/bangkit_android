package com.example.bangkitandroid.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bangkitandroid.R
import com.example.bangkitandroid.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setupView()
    }

    private fun setupView() {
        // set photo with extra url
        binding.editName.hint = intent.getStringExtra(EXTRA_NAME).toString()
        binding.editPhone.hint = intent.getStringExtra(EXTRA_PHONE).toString()

        binding.saveButton.setOnClickListener {
            val name = binding.editName.text.toString()
            val phone = binding.editPhone.text.toString()

            when {
                name.isEmpty() -> {
                    binding.editName.error = resources.getString(R.string.fill_name)
                }
                phone.isEmpty() -> {
                    binding.editPhone.error = resources.getString(R.string.fill_phone)
                }
                else -> {
                    // call save profile
                    finish() // for now
                }
            }
        }
    }

    companion object {
        // can be just 1 user object, later get its name, phone, and photo
        const val EXTRA_PHOTO = "PHOTO"
        const val EXTRA_NAME = "NAME"
        const val EXTRA_PHONE = "PHONE"
    }
}