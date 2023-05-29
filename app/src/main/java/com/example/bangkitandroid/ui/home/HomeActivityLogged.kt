package com.example.bangkitandroid.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bangkitandroid.R
import com.example.bangkitandroid.databinding.ActivityHomeLoggedBinding
import com.example.bangkitandroid.domain.entities.Blog
import com.example.bangkitandroid.domain.entities.History
import com.example.bangkitandroid.service.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.example.bangkitandroid.service.Result

class HomeActivityLogged : AppCompatActivity() {
    private val viewModel: HomeViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityHomeLoggedBinding
    private var histories: List<History> = emptyList()
    private var blogs: List<Blog> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeLoggedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        getData()
        setupView()
        setupBottomNavigationView()
    }

    private fun getData() {
        viewModel.getHome("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNjg1NDA5NjIwLCJpYXQiOjE2ODUzNjY0MjAsImp0aSI6ImVkZTI4MjY3Zjc0YjQ0MGY4MDU3ODMxYzRmODg2ZTk0IiwidXNlcl9pZCI6OX0.sUXbhtdGDI23rSVbzDU4nXsdYLOejbwq2XdJWlMBcDM")

        viewModel.getHistory().observe(this) {
            if (it != null) {
                when (it) {
                    is Result.Loading -> {

                    }
                    is Result.Success -> {
                        histories = it.data
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

        viewModel.getBlog().observe(this) {
            if (it != null) {
                when (it) {
                    is Result.Loading -> {

                    }
                    is Result.Success -> {
                        blogs = it.data
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

    private fun setupView() {
        // conditional view here, if not logged intent to home not logged

        binding.seeAllTv.setOnClickListener {
            // intent to disease history page
        }

        val historyAdapter = HistoryAdapter(histories)
        historyAdapter.setOnItemTapCallback(object : HistoryAdapter.OnItemTapCallback{
            override fun onItemTap(data: History) {
                // intent to disease detail
            }
        })

        val blogAdapter = BlogAdapter(blogs)
        blogAdapter.setOnItemTapCallback(object : BlogAdapter.OnItemTapCallback{
            override fun onItemTap(data: Blog) {
                // intent to blog detail
            }
        })

        binding.apply {
            historyRv.layoutManager = LinearLayoutManager(
                this@HomeActivityLogged,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            historyRv.adapter = historyAdapter

            blogRv.layoutManager = LinearLayoutManager(this@HomeActivityLogged)
            blogRv.adapter = blogAdapter
        }
    }

    private fun setupBottomNavigationView() {
        binding.bottomNavigation.selectedItemId = R.id.home
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    false
                }
                R.id.blog -> {
                    // Intent to blog page
                    finish()
                    true
                }
                R.id.profile -> {
                    // Intent to profile page
                    finish()
                    true
                }
                else -> false
            }
        }
    }
}