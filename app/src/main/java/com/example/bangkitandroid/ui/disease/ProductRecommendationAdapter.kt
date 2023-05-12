package com.example.bangkitandroid.ui.disease

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bangkitandroid.R
import com.example.bangkitandroid.databinding.VerticalCardItemBinding
import com.example.bangkitandroid.domain.entities.Product

class ProductRecommendationAdapter(private val products: List<Product>) : RecyclerView.Adapter<ProductRecommendationAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: VerticalCardItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(product: Product){
            binding.tvTitleVerticalItem.text = product.title
            binding.tvSubtitleVerticalItem.text = product.price
            Glide.with(binding.imgVerticalItem.context)
                .load(product.imgUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .into(binding.imgVerticalItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = VerticalCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product)
    }
}