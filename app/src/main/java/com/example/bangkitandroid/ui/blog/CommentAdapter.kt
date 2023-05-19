package com.example.bangkitandroid.ui.blog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bangkitandroid.R
import com.example.bangkitandroid.databinding.CommentCardItemBinding
import com.example.bangkitandroid.databinding.VerticalCardItemBinding
import com.example.bangkitandroid.domain.entities.Comment

class CommentAdapter(private val comments: List<Comment>) : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentAdapter.ViewHolder {
        val binding = CommentCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentAdapter.ViewHolder, position: Int) {
        val comment = comments[position]
        holder.bind(comment)
    }

    override fun getItemCount(): Int = comments.size
}