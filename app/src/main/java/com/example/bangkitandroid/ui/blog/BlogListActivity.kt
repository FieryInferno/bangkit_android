package com.example.bangkitandroid.ui.blog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bangkitandroid.R
import com.example.bangkitandroid.data.remote.model.BlogModel
import com.example.bangkitandroid.data.remote.response.BlogResponse
import com.example.bangkitandroid.databinding.ActivityBlogListBinding
import com.example.bangkitandroid.domain.entities.Blog
import com.example.bangkitandroid.service.DummyData
import com.example.bangkitandroid.service.ViewModelFactory
import com.example.bangkitandroid.ui.home.HomeActivityNotLogged
import com.example.bangkitandroid.ui.profile.ProfileLoggedActivity
import com.example.bangkitandroid.ui.profile.ProfileNotLoggedActivity

class BlogListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBlogListBinding
    private lateinit var blogs: List<Blog>
    private val viewModel: BlogViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBlogListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        getData()
        setView()
        setupBottomNavigationView()
    }

    private fun getData(){
        blogs = DummyData().getListBlogs()
    }

    private fun setView() {
        val layoutManager =LinearLayoutManager(this)
        binding.rvListBlog.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvListBlog.addItemDecoration(itemDecoration)

        val adapter = BlogAdapter()
        adapter.setOnItemTapCallback(object : BlogAdapter.OnItemTapCallback{
            override fun onItemTap(data: BlogModel) {
                val detailBlogIntent = Intent(this@BlogListActivity, BlogDetailActivity::class.java)
                detailBlogIntent.putExtra(BlogDetailActivity.EXTRA_BLOG, data.id.toString())
                startActivity(detailBlogIntent)
            }
        })
        binding.rvListBlog.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter{
                adapter.retry()
            }
        )
        viewModel.listBlog.observe(this) {
            adapter.submitData(lifecycle, it)
        }
    }

    private fun setupBottomNavigationView() {
        binding.bottomNavigation.selectedItemId = R.id.blog
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    val homeIntent = Intent(this@BlogListActivity, HomeActivityNotLogged::class.java)
                    startActivity(homeIntent)
                    finish()
                    true
                }
                R.id.blog -> {
                    false
                }
                R.id.profile -> {
                    viewModel.getSessionId().observe(this){ session ->
                        if (session.isEmpty()) {
                            val profileIntent = Intent(this, ProfileNotLoggedActivity::class.java)
                            startActivity(profileIntent)
                        } else {
                            val profileIntent = Intent(this, ProfileLoggedActivity::class.java)
                            startActivity(profileIntent)
                        }
                    }
                    finish()
                    true
                }
                else -> false
            }
        }
    }

}