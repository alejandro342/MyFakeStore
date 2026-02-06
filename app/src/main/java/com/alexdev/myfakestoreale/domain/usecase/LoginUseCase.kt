package com.alexdev.myfakestoreale.domain.usecase

import com.alexdev.myfakestoreale.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(username: String, password: String): Result<String> {

        // validaciones de negocio
        if (username.isBlank() || password.isBlank()) {
            return Result.failure(Exception("El usuario y contraseña no pueden estar vacíos"))
        }

        // llamamos al repositorio
        return repository.login(username, password)
    }
}