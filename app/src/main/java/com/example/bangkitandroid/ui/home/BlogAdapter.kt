package com.example.bangkitandroid.ui.home

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bangkitandroid.R
import com.example.bangkitandroid.databinding.HorizontalCardItemBinding
import com.example.bangkitandroid.domain.entities.Blog
import com.example.bangkitandroid.service.DateFormatter
import java.util.TimeZone

class BlogAdapter(private val blogs: List<Blog>) : RecyclerView.Adapter<BlogAdapter.ViewHolder>() {
    private lateinit var onItemTapCallback: OnItemTapCallback

    inner class ViewHolder(private val binding: HorizontalCardItemBinding) : RecyclerView.ViewHolder(binding.root){
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(blog: Blog){
            binding.tvTitleHorizontalItem.text = blog.title
            binding.tvSubtitleHorizontalItem.text = DateFormatter.formatDate(blog.dateTime, TimeZone.getDefault().id)
            Glide.with(binding.imgHorizontalItem.context)
                .load(blog.imgUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .into(binding.imgHorizontalItem)
            binding.tvSecondSubtitleHorizontalItem.text = blog.user.toString()
            binding.tvSecondSubtitleHorizontalItem.visibility = View.VISIBLE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogAdapter.ViewHolder {
        val binding = HorizontalCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: BlogAdapter.ViewHolder, position: Int) {
        val blog = blogs[position]
        holder.bind(blog)
        holder.itemView.setOnClickListener {
            onItemTapCallback.onItemTap(blog)
        }
    }

    override fun getItemCount(): Int = blogs.size

    fun setOnItemTapCallback(onItemTapCallback: OnItemTapCallback){
        this.onItemTapCallback = onItemTapCallback
    }

    interface OnItemTapCallback {
        fun onItemTap(data: Blog)
    }
}