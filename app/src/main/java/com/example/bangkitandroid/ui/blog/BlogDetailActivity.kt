package com.example.bangkitandroid.ui.blog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.bangkitandroid.R
import com.example.bangkitandroid.databinding.ActivityBlogDetailBinding
import com.example.bangkitandroid.domain.entities.Blog
import com.example.bangkitandroid.service.DummyData

class BlogDetailActivity : AppCompatActivity() {
    private lateinit var blog: Blog
    private var binding: ActivityBlogDetailBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBlogDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        supportActionBar?.hide()
        getData()
        setView()
    }

    private fun getData() {
        blog = DummyData().getDetailBlog(0)
    }
    private fun setView() {
        val adapter = CommentAdapter()
        binding?.apply {
            tvBlogName.text = blog.title
            tvDiseaseDescription.text = blog.description
            tvBlogDateTime.text = blog.dateTime
            tvBlogAuthor.text = blog.user.toString()
            Glide.with(this@BlogDetailActivity)
                .load(blog.imgUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .into(imgBlogDetail)
            rvComment.layoutManager = LinearLayoutManager(this@BlogDetailActivity, LinearLayoutManager.VERTICAL, false)
            rvComment.adapter = adapter
            btnBlogBack.setOnClickListener {
                finish()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object{
        const val EXTRA_BLOG = "extra_blog"
    }
}