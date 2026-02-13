package com.alexdev.myfakestoreale.domain.repository

import com.alexdev.myfakestoreale.data.model.ProductModel

interface ProductRepository {
    suspend fun getProducts(): Result<List<ProductModel>>
}