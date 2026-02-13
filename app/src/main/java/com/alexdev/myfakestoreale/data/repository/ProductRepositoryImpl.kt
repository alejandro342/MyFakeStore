package com.alexdev.myfakestoreale.data.repository

import com.alexdev.myfakestoreale.data.model.ProductModel
import com.alexdev.myfakestoreale.data.remote.ApiService
import com.alexdev.myfakestoreale.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: ApiService
) : ProductRepository {

    override suspend fun getProducts(): Result<List<ProductModel>> {
        return try {
            val response = api.getAllProducts()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}