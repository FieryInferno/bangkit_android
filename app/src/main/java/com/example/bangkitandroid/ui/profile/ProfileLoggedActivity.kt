package com.example.bangkitandroid.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.bangkitandroid.R
import com.example.bangkitandroid.databinding.ActivityProfileLoggedBinding
import com.example.bangkitandroid.domain.entities.User
import com.example.bangkitandroid.service.DummyData

class ProfileLoggedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileLoggedBinding
    private lateinit var user: User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileLoggedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        getData()
        setupView()
        setupBottomNavigationView()
    }

    private fun getData() {
        user =  DummyData().getUser(1)
    }

    private fun setupView() {
        Glide.with(this).load(user.imgUrl).circleCrop().into(binding.personPhoto)
        binding.personName.text = user.name
        binding.personPhone.text = user.phoneNumber

        binding.editTv.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            intent.putExtra(EditProfileActivity.EXTRA_USER, user)
            startActivity(intent)
        }

        binding.logoutButton.setOnClickListener {
            // logout user and intent to home not logged in
        }
    }

    private fun setupBottomNavigationView() {
        binding.bottomNavigation.selectedItemId = R.id.profile
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    // Intent to home page
                    finish()
                    true
                }
                R.id.blog -> {
                    // Intent to blog page
                    finish()
                    true
                }
                R.id.profile -> {
                    false
                }
                else -> false
            }
        }
    }
}