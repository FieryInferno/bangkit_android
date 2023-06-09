package com.example.bangkitandroid.ui.blog

import android.annotation.SuppressLint
import android.content.Intent
import android.nfc.NfcAdapter.EXTRA_ID
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.LoadState
import androidx.paging.map
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListUpdateCallback
import com.bumptech.glide.Glide
import com.example.bangkitandroid.R
import com.example.bangkitandroid.data.remote.response.BlogResponse
import com.example.bangkitandroid.databinding.ActivityBlogDetailBinding
import com.example.bangkitandroid.domain.entities.Blog
import com.example.bangkitandroid.domain.entities.Comment
import com.example.bangkitandroid.domain.entities.User
import com.example.bangkitandroid.service.DateFormatter
import com.example.bangkitandroid.service.DummyData
import com.example.bangkitandroid.service.Result
import com.example.bangkitandroid.service.ViewModelFactory
import com.example.bangkitandroid.ui.home.HomeActivityLogged
import com.example.bangkitandroid.ui.home.HomeActivityNotLogged
import kotlinx.coroutines.Dispatchers
import java.util.TimeZone

class BlogDetailActivity : AppCompatActivity() {
    private lateinit var blog: BlogResponse
    private var binding: ActivityBlogDetailBinding? = null
    private val viewModel: BlogViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }
    @RequiresApi(Build.VERSION_CODES.O)
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
    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setView() {
        viewModel.getSessionId().observe(this) {session ->
            if (session.isEmpty()) {
                binding?.tvLoginForComment?.visibility = View.VISIBLE
                binding?.etComment?.visibility = View.GONE
            } else {
                binding?.tvLoginForComment?.visibility = View.GONE
                binding?.etComment?.visibility = View.VISIBLE
            }
        }

        val idBlog = intent.getStringExtra(EXTRA_BLOG)?.toInt()
        if (idBlog != null) {
            viewModel.getBlog(idBlog).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            binding?.progressBar?.visibility = View.VISIBLE
                        }

                        is Result.Success -> {
                            binding?.progressBar?.visibility = View.GONE
                            val blogData = result.data
                            submitBlogData(blogData)
                        }

                        is Result.Error -> {
                            binding?.progressBar?.visibility = View.GONE
                            val errorMessage = result.error
                            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
        binding?.imgSend?.setOnClickListener {
            val inputComment = binding?.etComment?.text.toString()
            Log.e("commet", inputComment)
            if (inputComment.isEmpty()){
                binding?.etComment?.error = "Komentar Kosong"
                Handler(Looper.getMainLooper()).postDelayed({
                    binding?.etComment?.error = null
                }, 3000)

            } else {
                if (idBlog != null){
                    viewModel.postComment(inputComment, idBlog).observe(this) {
                        if (it != null) {
                            when (it) {
                                is Result.Loading -> {
                                    binding?.progressBar?.visibility = View.VISIBLE
                                }

                                is Result.Success -> {
                                    binding?.progressBar?.visibility = View.GONE
                                    recreate()
                                }

                                is Result.Error -> {
                                    binding?.progressBar?.visibility = View.GONE
                                    val errorMessage = it.error
                                    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun submitBlogData(data: Blog) {
        binding?.apply {
            tvBlogName.text = data.title
            tvDiseaseDescription.text = data.description
            tvBlogDateTime.text = DateFormatter.formatDate(data.timestamp, TimeZone.getDefault().id)
            tvBlogAuthor.text = data.user.name
            Glide.with(this@BlogDetailActivity)
                .load(data.image)
                .placeholder(R.drawable.ic_launcher_background)
                .into(imgBlogDetail)

            val commentAdapter = CommentAdapter()
            rvComment.layoutManager = LinearLayoutManager(this@BlogDetailActivity, LinearLayoutManager.VERTICAL, false)
            rvComment.adapter = commentAdapter.withLoadStateFooter(
                footer = LoadingStateAdapter{
                    commentAdapter.retry()
                }
            )
            viewModel.listComment(data.id).observe(this@BlogDetailActivity) {
                commentAdapter.submitData(lifecycle, it)
            }

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

val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}