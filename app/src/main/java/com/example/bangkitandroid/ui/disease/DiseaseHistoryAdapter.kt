package com.example.bangkitandroid.ui.disease

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bangkitandroid.R
import com.example.bangkitandroid.databinding.HorizontalCardItemBinding
import com.example.bangkitandroid.domain.entities.Disease

class DiseaseHistoryAdapter(private val diseases: List<Disease>) : RecyclerView.Adapter<DiseaseHistoryAdapter.ViewHolder>(){
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

    override fun getItemCount(): Int = diseases.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val disease = diseases[position]
        holder.bind(disease)
        holder.itemView.setOnClickListener {
            onItemTapCallback.onItemTap(disease)
        }
    }

    fun setOnItemTapCallback(onItemTapCallback: OnItemTapCallback){
        this.onItemTapCallback = onItemTapCallback
    }

    interface OnItemTapCallback {
        fun onItemTap(data: Disease)
    }
}