package com.example.bangkitandroid.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bangkitandroid.R
import com.example.bangkitandroid.databinding.ActivityProfileLoggedBinding

class ProfileLoggedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileLoggedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileLoggedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setupView()
        setupBottomNavigationView()
    }

    private fun setupView() {
        // set photo, name, phone number here

        binding.editTv.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            intent.putExtra(EditProfileActivity.EXTRA_NAME, "dummy name")
            intent.putExtra(EditProfileActivity.EXTRA_PHONE, "08123456789")
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