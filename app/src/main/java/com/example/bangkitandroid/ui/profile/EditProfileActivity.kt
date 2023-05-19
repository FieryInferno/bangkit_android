package com.example.bangkitandroid.ui.profile

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.bangkitandroid.R
import com.example.bangkitandroid.databinding.ActivityEditProfileBinding
import com.example.bangkitandroid.domain.entities.User

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
        val user = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra<User>(EXTRA_USER, User::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<User>(EXTRA_USER)
        }

        Glide.with(this).load(user?.imgUrl).into(binding.editPhoto)
        binding.editName.hint = user?.name
        binding.editPhone.hint = user?.phoneNumber

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
        const val EXTRA_USER = "USER"
    }
}