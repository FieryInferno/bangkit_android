package com.example.bangkitandroid.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.bangkitandroid.R
import com.example.bangkitandroid.databinding.ActivityProfileLoggedBinding
import com.example.bangkitandroid.domain.entities.User
import com.example.bangkitandroid.service.Result
import com.example.bangkitandroid.service.ViewModelFactory
import com.example.bangkitandroid.ui.blog.BlogListActivity
import com.example.bangkitandroid.ui.home.HomeActivityLogged
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
        viewModel.getUser().observe(this) {
            if (it != null) {
                when (it) {
                    is Result.Loading -> {
                        binding.apply {
                            personName.visibility = View.GONE
                            personPhone.visibility = View.GONE
                        }
                    }
                    is Result.Success -> {
                        user = it.data

                        binding.apply {
                            if (user.imgUrl != "") {
                                Glide.with(this@ProfileLoggedActivity).load(user.imgUrl).into(personPhoto)
                            } else {
                                personPhoto.setImageResource(R.drawable.image_profile_default)
                            }

                            personName.visibility = View.VISIBLE
                            personPhone.visibility = View.VISIBLE

                            personName.text = user.name
                            personPhone.text = user.phoneNumber

                            editTv.setOnClickListener {
                                val intent = Intent(this@ProfileLoggedActivity, EditProfileActivity::class.java)
                                intent.putExtra(EditProfileActivity.EXTRA_USER, user)
                                startActivity(intent)
                            }
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


        binding.logoutButton.setOnClickListener {
            viewModel.logout()
            finish()
            startActivity(Intent(this, HomeActivityNotLogged::class.java))
        }
    }

    private fun setupBottomNavigationView() {
        binding.bottomNavigation.selectedItemId = R.id.profile
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    startActivity(Intent(this, HomeActivityLogged::class.java))
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