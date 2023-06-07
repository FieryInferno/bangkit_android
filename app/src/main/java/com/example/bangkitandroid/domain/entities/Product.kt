package com.example.bangkitandroid.domain.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product (
    val id: Int,
    val title: String,
    val description: String,
    val url: String,
    val price: Int,
    val imgUrl: String,
    val createdAt: String,
    val updatedAt: String,
) : Parcelable