package com.example.bangkitandroid.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bangkitandroid.R
import com.example.bangkitandroid.databinding.ActivityProfileNotLoggedBinding
import com.example.bangkitandroid.ui.authentication.LoginActivity
import com.example.bangkitandroid.ui.authentication.RegisterActivity
import com.example.bangkitandroid.ui.blog.BlogListActivity
import com.example.bangkitandroid.ui.home.HomeActivityNotLogged

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
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.loginButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun setupBottomNavigationView() {
        binding.bottomNavigation.selectedItemId = R.id.profile
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    startActivity(Intent(this, HomeActivityNotLogged::class.java))
                    finish()
                    true
                }
                R.id.blog -> {
                    startActivity(Intent(this, BlogListActivity::class.java))
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