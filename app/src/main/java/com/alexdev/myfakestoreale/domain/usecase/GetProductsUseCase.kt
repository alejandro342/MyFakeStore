package com.alexdev.myfakestoreale.domain.usecase

import com.alexdev.myfakestoreale.domain.model.Product
import com.alexdev.myfakestoreale.domain.model.toDomain
import com.alexdev.myfakestoreale.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {

    suspend operator fun invoke(): Result<List<Product>> {
        val result = repository.getProducts()

        return if (result.isSuccess) {
            val dataModels = result.getOrNull() ?: emptyList()
            val domainProducts = dataModels.map { it.toDomain() }

            Result.success(domainProducts)
        } else {
            Result.failure(result.exceptionOrNull() ?: Exception("Error desconocido"))
        }
    }
}