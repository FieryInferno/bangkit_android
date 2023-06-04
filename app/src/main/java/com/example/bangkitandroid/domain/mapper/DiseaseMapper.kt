package com.example.bangkitandroid.domain.mapper

import com.example.bangkitandroid.data.remote.model.HistoryModel
import com.example.bangkitandroid.data.remote.model.ProductModel
import com.example.bangkitandroid.data.remote.response.DiseaseHistoryResponse
import com.example.bangkitandroid.data.remote.response.DiseaseResponse
import com.example.bangkitandroid.domain.entities.Disease
import com.example.bangkitandroid.domain.entities.History
import com.example.bangkitandroid.domain.entities.Product

fun DiseaseHistoryResponse.toListDisease() : List<Disease> = result.toListDisease()

fun List<DiseaseResponse>.toListDisease() : List<Disease> = map {
    Disease(
        id = it.data.disease.id,
        title = it.data.disease.name,
        description = it.data.disease.description,
        image = it.data.image,
        treatment = it.data.disease.solution,
        createdAt = it.data.disease.createdAt,
        updatedAt = it.data.disease.updatedAt,
        products = it.data.disease.products.toListProduct()
    )
}


fun DiseaseResponse.toDisease() : Disease = let {
    Disease(
        id = it.data.disease.id,
        title = it.data.disease.name,
        description = it.data.disease.description,
        image = it.data.image,
        treatment = it.data.disease.solution,
        createdAt = it.data.disease.createdAt,
        updatedAt = it.data.disease.updatedAt,
        products = it.data.disease.products.toListProduct()
    )
}

fun List<ProductModel>.toListProduct() : List<Product> = map {
    Product(
        id = it.id,
        title = it.title,
        description = it.description,
        url = it.url,
        imgUrl = it.image,
        price = it.price,
        createdAt = it.createdAt,
        updatedAt = it.updatedAt
    )
}

fun List<HistoryModel>.toListHistory() : List<History> = map {
    History(
        image = it.image,
        id = it.id,
        user = it.user,
        timestamp = it.timestamp,
        disease = Disease(
            id = it.disease.id,
            title = it.disease.name,
            description = it.disease.description,
            image = it.image,
            treatment = it.disease.solution,
            createdAt = it.disease.createdAt,
            updatedAt = it.disease.updatedAt,
            products = it.disease.products.toListProduct()
        )
    )
}