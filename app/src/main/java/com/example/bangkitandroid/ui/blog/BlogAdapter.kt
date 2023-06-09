package com.example.bangkitandroid.ui.blog

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bangkitandroid.R
import com.example.bangkitandroid.databinding.HorizontalCardItemBinding
import com.example.bangkitandroid.domain.entities.Blog
import com.example.bangkitandroid.service.DateFormatter
import java.util.TimeZone

class BlogAdapter : PagingDataAdapter<Blog, BlogAdapter.ViewHolder>(
    DIFF_CALLBACK
) {
    private lateinit var onItemTapCallback: OnItemTapCallback

    inner class ViewHolder(private val binding: HorizontalCardItemBinding) : RecyclerView.ViewHolder(binding.root){
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(blog: Blog){
            Log.e("paging", blog.toString())
            binding.tvTitleHorizontalItem.text = blog.title
            binding.tvSubtitleHorizontalItem.text = DateFormatter.formatDate(blog.timestamp, TimeZone.getDefault().id)
            Glide.with(binding.imgHorizontalItem.context)
                .load(blog.image)
                .placeholder(R.drawable.ic_launcher_background)
                .into(binding.imgHorizontalItem)
            binding.tvSecondSubtitleHorizontalItem.text = blog.user.name
            binding.tvSecondSubtitleHorizontalItem.visibility = View.VISIBLE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HorizontalCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
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