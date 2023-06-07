package com.example.bangkitandroid.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bangkitandroid.databinding.HorizontalCardItemPlaceholderBinding

class BlogPlaceholderAdapter : RecyclerView.Adapter<BlogPlaceholderAdapter.ViewHolder>() {
    inner class ViewHolder(binding: HorizontalCardItemPlaceholderBinding) : RecyclerView.ViewHolder(binding.root) {
        // view class
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HorizontalCardItemPlaceholderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // no need to bind data here
    }

    override fun getItemCount(): Int = 5
}