package com.alexdev.myfakestoreale.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexdev.myfakestoreale.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import retrofit2.HttpException
import java.io.IOException

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            val result = loginUseCase(username, password)

            result.onSuccess { token ->
                //storeManager.saveToken(token)
                _state.update {
                    it.copy(isLoading = false, token = token, error = null)
                }
            }.onFailure { exception ->
                val errorMessage = when (exception) {
                    is HttpException -> {
                        when (exception.code()) {
                            401 -> "Usuario o contraseña incorrectos."
                            404 -> "El servicio no se encuentra disponible."
                            500, 502 -> "Error interno del servidor."
                            else -> "Error de comunicación: ${exception.code()}"
                        }
                    }
                    is IOException -> "No tienes conexión a internet."
                    else -> "Ocurrió un error inesperado: ${exception.localizedMessage}"
                }

                _state.update {
                    it.copy(isLoading = false, error = errorMessage)
                }
            }
        }
    }

    fun clearState() {
        _state.update { LoginState() }
    }
}