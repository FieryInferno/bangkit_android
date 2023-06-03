package com.example.bangkitandroid.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bangkitandroid.R
import com.example.bangkitandroid.databinding.ActivityProfileNotLoggedBinding
import com.example.bangkitandroid.ui.authentication.LoginActivity
import com.example.bangkitandroid.ui.authentication.RegisterActivity

class ProfileNotLoggedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileNotLoggedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileNotLoggedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setupView()
        setupBottomNavigationView()
    }

    private fun setupView() {
        binding.registerButton.setOnClickListener {
            // Intent to register page
            val registerIntent = Intent(this@ProfileNotLoggedActivity, RegisterActivity::class.java)
            startActivity(registerIntent)
        }

        binding.loginButton.setOnClickListener {
            // Intent to login page
            val loginIntent = Intent(this@ProfileNotLoggedActivity, LoginActivity::class.java)
            startActivity(loginIntent)
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