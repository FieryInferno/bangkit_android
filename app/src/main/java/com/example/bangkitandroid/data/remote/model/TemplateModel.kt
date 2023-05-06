package com.example.bangkitandroid.data.remote.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TemplateModel (
    val message: String? = null
): Parcelable