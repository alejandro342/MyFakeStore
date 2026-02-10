package com.alexdev.myfakestoreale.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexdev.myfakestoreale.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LoginUiState())
    val state: StateFlow<LoginUiState> = _state.asStateFlow()

    private val _effect = Channel<LoginSideEffect>()
    val effect = _effect.receiveAsFlow()

    fun onEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.OnUsernameChanged -> {

                _state.update { it.copy(username = event.value, usernameError = false) }
            }

            is LoginUiEvent.OnPasswordChanged -> {
                _state.update { it.copy(password = event.value, passwordError = false) }
            }

            is LoginUiEvent.OnRememberMeChanged -> {
                _state.update { it.copy(checkedState = event.value) }
            }

            is LoginUiEvent.OnLoginClicked -> {
                validateAndLogin()
            }

            is LoginUiEvent.OnForgotPasswordClicked -> {
                sendEffect(LoginSideEffect.ShowToast("Recuperar contraseña (TODO)"))
            }

            is LoginUiEvent.OnRegisterClicked -> {
                sendEffect(LoginSideEffect.NavigateToRegister)
            }

            else -> {}
        }
    }

    private fun sendEffect(effect: LoginSideEffect) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }

    private fun validateAndLogin() {
        val user = _state.value.username
        val pass = _state.value.password

        val hasUserError = user.isBlank()
        val hasPassError = pass.isBlank()

        if (hasUserError || hasPassError) {
            _state.update { it.copy(usernameError = hasUserError, passwordError = hasPassError) }
            return
        }
        performLogin(user, pass)
    }

    private fun performLogin(user: String, pass: String) {
        viewModelScope.launch {

            _state.update { it.copy(isLoading = true, loginError = null) }

            loginUseCase(user, pass)
                .onSuccess { token ->
                    //storeManager.saveToken(token)
                    _state.update { it.copy(isLoading = false, isLoginSuccess = true) }
                    sendEffect(LoginSideEffect.NavigateToHome)
                }
                .onFailure { exception ->
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
                        it.copy(
                            isLoading = false,
                            loginError = errorMessage
                        )
                    }
                }
        }
    }

    fun clearState() {
        _state.update { currentState ->
            currentState.copy(
                isLoginSuccess = false,
                loginError = null,
                isLoading = false,
                usernameError = false,
                passwordError = false,
                password = "",
                username = ""
            )
        }
    }
}