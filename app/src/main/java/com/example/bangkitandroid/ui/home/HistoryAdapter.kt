package com.example.bangkitandroid.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bangkitandroid.R
import com.example.bangkitandroid.databinding.VerticalCardItemBinding
import com.example.bangkitandroid.domain.entities.Disease

class HistoryAdapter(private val diseases: List<Disease>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    private lateinit var onItemTapCallback: OnItemTapCallback

    inner class ViewHolder(private val binding: VerticalCardItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(disease: Disease){
            binding.tvTitleVerticalItem.text = disease.title
            binding.tvSubtitleVerticalItem.text = disease.dateTime
            Glide.with(binding.imgVerticalItem.context)
                .load(disease.imgUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .into(binding.imgVerticalItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = VerticalCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = diseases.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val disease = diseases[position]
        holder.bind(disease)
    }

    fun setOnItemTapCallback(onItemTapCallback: OnItemTapCallback){
        this.onItemTapCallback = onItemTapCallback
    }

    interface OnItemTapCallback {
        fun onItemTap(data: Disease)
    }
}