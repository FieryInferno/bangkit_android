package com.example.bangkitandroid.ui.blog

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bangkitandroid.R
import com.example.bangkitandroid.data.remote.response.BlogResponse
import com.example.bangkitandroid.databinding.HorizontalCardItemBinding
import com.example.bangkitandroid.domain.entities.Blog
import com.example.bangkitandroid.domain.entities.Disease
import com.example.bangkitandroid.service.formatDateTime

class BlogAdapter : PagingDataAdapter<BlogResponse, BlogAdapter.ViewHolder>(
    DIFF_CALLBACK
) {
    private lateinit var onItemTapCallback: OnItemTapCallback

    inner class ViewHolder(private val binding: HorizontalCardItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(blog: BlogResponse){
            binding.tvTitleHorizontalItem.text = blog.title
            binding.tvSubtitleHorizontalItem.text = formatDateTime(blog.timestamp)
            Glide.with(binding.imgHorizontalItem.context)
                .load(blog.image)
                .placeholder(R.drawable.ic_launcher_background)
                .into(binding.imgHorizontalItem)
            binding.tvSecondSubtitleHorizontalItem.text = "author"
            binding.tvSecondSubtitleHorizontalItem.visibility = View.VISIBLE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HorizontalCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val blog = getItem(position)
        if(blog != null){
            holder.bind(blog)
            holder.itemView.setOnClickListener {
                onItemTapCallback.onItemTap(blog)
            }
        }
    }

    fun setOnItemTapCallback(onItemTapCallback: OnItemTapCallback){
        this.onItemTapCallback = onItemTapCallback
    }

    interface OnItemTapCallback {
        fun onItemTap(data: BlogResponse)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<BlogResponse>() {
            override fun areItemsTheSame(oldItem: BlogResponse, newItem: BlogResponse): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: BlogResponse, newItem: BlogResponse): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}