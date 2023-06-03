package com.example.bangkitandroid.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bangkitandroid.R
import com.example.bangkitandroid.databinding.ActivityHomeNotLoggedBinding
import com.example.bangkitandroid.domain.entities.Blog
import com.example.bangkitandroid.service.DummyData
import com.example.bangkitandroid.ui.profile.ProfileNotLoggedActivity

class HomeActivityNotLogged : AppCompatActivity() {
    private lateinit var binding: ActivityHomeNotLoggedBinding
    private lateinit var blogs: List<Blog>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeNotLoggedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        getData()
        setupView()
        setupBottomNavigationView()
    }

    private fun getData() {
        blogs = DummyData().getListBlogs()
    }

    private fun setupView() {
        val blogAdapter = BlogAdapter(blogs)
        blogAdapter.setOnItemTapCallback(object : BlogAdapter.OnItemTapCallback{
            override fun onItemTap(data: Blog) {
                // intent to blog detail
            }
        })

        binding.apply {
            blogRv.layoutManager = LinearLayoutManager(this@HomeActivityNotLogged)
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
                    // Intent to profile page not logged
                    val profileIntent = Intent(this@HomeActivityNotLogged, ProfileNotLoggedActivity::class.java)
                    startActivity(profileIntent)
                    finish()
                    true
                }
                else -> false
            }
        }
    }
}