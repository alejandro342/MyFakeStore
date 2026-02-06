package com.alexdev.myfakestoreale.data.repository

import com.alexdev.myfakestoreale.data.remote.ApiService
import com.alexdev.myfakestoreale.data.remote.dto.LoginRequest
import com.alexdev.myfakestoreale.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: ApiService
) : AuthRepository {

    override suspend fun login(username: String, password: String): Result<String> {
        return try {
            val request = LoginRequest(username, password)
            val response = api.login(request)

            // API respondió bien. Devolvemos el token.
            Result.success(response.token)

        } catch (e: Exception) {
            // Si hay error (401, sin internet, etc), devolvemos el fallo.
            e.printStackTrace()
            Result.failure(e)
        }
    }
}