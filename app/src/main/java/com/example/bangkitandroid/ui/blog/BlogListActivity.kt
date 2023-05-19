package com.example.bangkitandroid.ui.blog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bangkitandroid.R
import androidx.annotation.StringRes
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bangkitandroid.R
import com.example.bangkitandroid.databinding.ActivityBlogBinding
import com.example.bangkitandroid.databinding.ActivityBlogListBinding
import com.example.bangkitandroid.domain.entities.Blog
import com.example.bangkitandroid.service.DummyData
import com.example.bangkitandroid.ui.disease.DiseaseDetailActivity

class BlogListActivity : AppCompatActivity() {
    private var binding: ActivityBlogListBinding? = null
    private lateinit var blogs: List<Blog>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBlogListBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        supportActionBar?.hide()
        getData()
        setView()
    }

    private fun getData(){
        blogs = DummyData().getListBlogs()
    }

    private fun setView() {
        val adapter = BlogAdapter(blogs)
        adapter.setOnItemTapCallback(object : BlogAdapter.OnItemTapCallback{
            override fun onItemTap(data: Blog) {
                val intent = Intent(this@BlogListActivity, BlogDetailActivity::class.java)
                startActivity(intent)
            }
        })
        binding?.apply {
            rvListBlog.layoutManager = LinearLayoutManager(this@BlogListActivity)
            rvListBlog.adapter = adapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}