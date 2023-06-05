package com.example.bangkitandroid.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.bangkitandroid.R
import com.example.bangkitandroid.databinding.ActivityHomeLoggedBinding
import com.example.bangkitandroid.databinding.ActivityProfileLoggedBinding
import com.example.bangkitandroid.domain.entities.User
import com.example.bangkitandroid.domain.mapper.toUser
import com.example.bangkitandroid.service.Result
import com.example.bangkitandroid.service.ViewModelFactory
import com.example.bangkitandroid.ui.blog.BlogListActivity
import com.example.bangkitandroid.ui.home.HomeActivityNotLogged
import com.google.android.material.snackbar.Snackbar

class ProfileLoggedActivity : AppCompatActivity() {
    private val viewModel: ProfileViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityProfileLoggedBinding
    private lateinit var user: User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileLoggedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setupView()
        setupBottomNavigationView()
    }

    private fun setupView() {
        viewModel.getToken().observe(this) { token ->
            viewModel.getUser(token).observe(this) {
                if (it != null) {
                    when (it) {
                        is Result.Loading -> {

                        }
                        is Result.Success -> {
                            user = it.data.user.toUser()

                            Glide.with(this).load(user.imgUrl).circleCrop().into(binding.personPhoto)
                            binding.personName.text = user.name
                            binding.personPhone.text = user.phoneNumber

                            binding.editTv.setOnClickListener {
                                val intent = Intent(this, EditProfileActivity::class.java)
                                intent.putExtra(EditProfileActivity.EXTRA_USER, user)
                                startActivity(intent)
                            }
                        }
                        is Result.Error -> {
                            Snackbar.make(
                                window.decorView.rootView,
                                it.error,
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }

        binding.logoutButton.setOnClickListener {
            viewModel.logout()
            startActivity(Intent(this, HomeActivityNotLogged::class.java))
        }
    }

    private fun setupBottomNavigationView() {
        binding.bottomNavigation.selectedItemId = R.id.profile
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    startActivity(Intent(this, ActivityHomeLoggedBinding::class.java))
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