package com.example.bangkitandroid.ui.blog

import android.nfc.NfcAdapter.EXTRA_ID
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.bangkitandroid.R
import com.example.bangkitandroid.data.remote.response.BlogResponse
import com.example.bangkitandroid.databinding.ActivityBlogDetailBinding
import com.example.bangkitandroid.domain.entities.Blog
import com.example.bangkitandroid.service.DummyData
import com.example.bangkitandroid.service.Result
import com.example.bangkitandroid.service.ViewModelFactory
import com.example.bangkitandroid.service.formatDateTime

class BlogDetailActivity : AppCompatActivity() {
    private lateinit var blog: BlogResponse
    private var binding: ActivityBlogDetailBinding? = null
    private val viewModel: BlogViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBlogDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        supportActionBar?.hide()
        getData()
        setView()
    }

    private fun getData() {
        val idUser = intent.getStringExtra(EXTRA_BLOG)?.toInt()
        if (idUser != null){
            viewModel.getBlog(idUser)
        }
    }
    private fun setView() {
        val idUser = intent.getStringExtra(EXTRA_BLOG)?.toInt()
        if (idUser != null) {
            viewModel.getBlog(idUser).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {

                        }

                        is Result.Success -> {
                            val blogData = result.data
                            submitBlogData(blogData)
                        }

                        is Result.Error -> {

                        }
                    }
                }
            }
        }

        binding?.commentTextInputLayout?.setEndIconOnClickListener {
            val inputComment = binding?.commentEditText?.text.toString()

            Log.e("input", inputComment)
        }

    }

    private fun submitBlogData(data: BlogResponse) {
        val comment = DummyData().getCommentDummy()
        val commentAdapter = CommentAdapter(comment)
        binding?.apply {
            tvBlogName.text = data.title
            tvDiseaseDescription.text = data.description
            tvBlogDateTime.text = formatDateTime(data.timestamp)
            tvBlogAuthor.text = "author"
            Glide.with(this@BlogDetailActivity)
                .load(data.image)
                .placeholder(R.drawable.ic_launcher_background)
                .into(imgBlogDetail)

            rvComment.layoutManager = LinearLayoutManager(this@BlogDetailActivity, LinearLayoutManager.VERTICAL, false)
            rvComment.adapter = commentAdapter
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