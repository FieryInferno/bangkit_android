package com.example.bangkitandroid.ui.home

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bangkitandroid.R
import com.example.bangkitandroid.databinding.VerticalCardItemBinding
import com.example.bangkitandroid.domain.entities.History
import com.example.bangkitandroid.service.DateFormatter
import java.util.TimeZone

class HistoryAdapter(private val histories: List<History>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    private lateinit var onItemTapCallback: OnItemTapCallback

    inner class ViewHolder(private val binding: VerticalCardItemBinding) : RecyclerView.ViewHolder(binding.root){
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(history: History){
            binding.tvTitleVerticalItem.text = history.disease.title
            binding.tvSubtitleVerticalItem.text = DateFormatter.formatDate(history.timestamp, TimeZone.getDefault().id)
            Glide.with(binding.imgVerticalItem.context)
                .load(history.image)
                .placeholder(R.drawable.ic_launcher_background)
                .into(binding.imgVerticalItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = VerticalCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = histories.size

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val history = histories[position]
        holder.bind(history)
    }

    fun setOnItemTapCallback(onItemTapCallback: OnItemTapCallback){
        this.onItemTapCallback = onItemTapCallback
    }

    interface OnItemTapCallback {
        fun onItemTap(data: History)
    }
}