package com.example.bangkitandroid.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bangkitandroid.R
import com.example.bangkitandroid.databinding.ActivityHomeLoggedBinding
import com.example.bangkitandroid.domain.entities.Blog
import com.example.bangkitandroid.domain.entities.Disease
import com.example.bangkitandroid.service.DummyData

class HomeActivityLogged : AppCompatActivity() {
    private lateinit var binding: ActivityHomeLoggedBinding
    private lateinit var diseases: List<Disease>
    private lateinit var blogs: List<Blog>

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
        diseases = DummyData().getHistoryDiseases()
        blogs = DummyData().getListBlogs()
    }

    private fun setupView() {
        // conditional view here, if not logged intent to home not logged

        binding.seeAllTv.setOnClickListener {
            // intent to disease history page
        }

        val historyAdapter = HistoryAdapter(diseases)
        historyAdapter.setOnItemTapCallback(object : HistoryAdapter.OnItemTapCallback{
            override fun onItemTap(data: Disease) {
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