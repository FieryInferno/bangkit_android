package com.example.bangkitandroid.ui.blog

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bangkitandroid.R
import com.example.bangkitandroid.data.remote.model.BlogModel
import com.example.bangkitandroid.databinding.CommentCardItemBinding
import com.example.bangkitandroid.domain.entities.Comment
import com.example.bangkitandroid.service.DateFormatter
import com.example.bangkitandroid.ui.blog.BlogAdapter.Companion.DIFF_CALLBACK
import java.util.TimeZone

class CommentAdapter :
    PagingDataAdapter<Comment, CommentAdapter.ViewHolder>(
        DIFF_CALLBACK
) {

    inner class ViewHolder(private val binding: CommentCardItemBinding) : RecyclerView.ViewHolder(binding.root){
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(comment: Comment){
            binding.tvNameItem.text = comment.user.name
            binding.tvDateItem.text = DateFormatter.formatDate(comment.dateTime, TimeZone.getDefault().id)
            binding.tvDescriptionItem.text = comment.description
            Glide.with(binding.imgVerticalItem.context)
                .load(comment.user.imgUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .into(binding.imgVerticalItem)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CommentCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comment = getItem(position)
        if(comment != null){
            holder.bind(comment)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Comment>() {
            override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}