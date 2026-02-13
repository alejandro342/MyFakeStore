package com.alexdev.myfakestoreale.domain.model

import com.alexdev.myfakestoreale.data.model.ProductModel

data class Product(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: Double,
    val ratingCount: Int
)
// Función de extensión para convertir
fun ProductModel.toDomain(): Product {
    return Product(
        id = id,
        title = title,
        price = price,
        description = description,
        category = category,
        image = image,
        rating = rating.rate,
        ratingCount = rating.count
    )
}