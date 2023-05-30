package com.example.bangkitandroid.ui.blog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bangkitandroid.R
import com.example.bangkitandroid.databinding.HorizontalCardItemBinding
import com.example.bangkitandroid.domain.entities.Blog

class BlogAdapter : PagingDataAdapter<Blog, BlogAdapter.ViewHolder>(
    DIFF_CALLBACK) {
    private lateinit var onItemTapCallback: OnItemTapCallback

    inner class ViewHolder(private val binding: HorizontalCardItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(blog: Blog){
            binding.tvTitleHorizontalItem.text = blog.title
            binding.tvSubtitleHorizontalItem.text = blog.dateTime
            Glide.with(binding.imgHorizontalItem.context)
                .load(blog.imgUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .into(binding.imgHorizontalItem)
            binding.tvSecondSubtitleHorizontalItem.text = blog.user.toString()
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
        fun onItemTap(data: Blog)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Blog>() {
            override fun areItemsTheSame(oldItem: Blog, newItem: Blog): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Blog, newItem: Blog): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}