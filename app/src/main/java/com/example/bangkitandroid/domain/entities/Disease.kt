package com.example.bangkitandroid.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Disease (
    val id: Int,
    val products: List<Product>,
    val title: String,
    val description: String,
    val image: String?,
    val treatment: String,
    val createdAt: String,
    val updatedAt: String,
) : Parcelable