package com.example.bangkitandroid.ui.blog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bangkitandroid.R
import com.example.bangkitandroid.databinding.CommentCardItemBinding
import com.example.bangkitandroid.domain.entities.Comment

class CommentAdapter(private val comment: List<Comment>) :
    RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: CommentCardItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(comment: Comment){
            binding.tvNameItem.text = comment.user.name
            binding.tvDateItem.text = comment.dateTime
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

    override fun getItemCount(): Int = comment.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comment = comment[position]
        holder.bind(comment)
    }
}