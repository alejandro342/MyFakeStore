package com.alexdev.myfakestoreale.data.remote

import com.alexdev.myfakestoreale.data.model.ProductModel
import com.alexdev.myfakestoreale.data.remote.dto.LoginRequest
import com.alexdev.myfakestoreale.data.remote.dto.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse
    @GET("products")

    suspend fun getAllProducts(): List<ProductModel>
}