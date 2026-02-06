package com.alexdev.myfakestoreale.domain.repository

interface AuthRepository {
    // Devuelve el token
    suspend fun login(username: String, password: String): Result<String>
}