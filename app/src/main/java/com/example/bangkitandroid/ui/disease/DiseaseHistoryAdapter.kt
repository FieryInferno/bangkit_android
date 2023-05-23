package com.example.bangkitandroid.ui.disease

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bangkitandroid.R
import com.example.bangkitandroid.databinding.HorizontalCardItemBinding
import com.example.bangkitandroid.domain.entities.Disease

class DiseaseHistoryAdapter : PagingDataAdapter<Disease, DiseaseHistoryAdapter.ViewHolder>(
    DIFF_CALLBACK){
    private lateinit var onItemTapCallback: OnItemTapCallback


    inner class ViewHolder(private val binding: HorizontalCardItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(disease: Disease){
            binding.apply {
                tvTitleHorizontalItem.text = disease.title
                tvSubtitleHorizontalItem.text = disease.dateTime
                tvSecondSubtitleHorizontalItem.visibility = View.GONE
                Glide.with(imgHorizontalItem)
                    .load(disease.imgUrl)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(imgHorizontalItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HorizontalCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val disease = getItem(position)
        if(disease != null){
            holder.bind(disease)
            holder.itemView.setOnClickListener {
                onItemTapCallback.onItemTap(disease)
            }
        }

    }

    fun setOnItemTapCallback(onItemTapCallback: OnItemTapCallback){
        this.onItemTapCallback = onItemTapCallback
    }

    interface OnItemTapCallback {
        fun onItemTap(data: Disease)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Disease>() {
            override fun areItemsTheSame(oldItem: Disease, newItem: Disease): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Disease, newItem: Disease): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}