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
                _state.update {
                    it.copy(isLoading = false, token = token, error = null)
                }
            }.onFailure { exception ->
                _state.update {
                    it.copy(isLoading = false, error = exception.message ?: "Error desconocido")
                }
            }
        }
    }

    fun clearState() {
        _state.update { LoginState() }
    }
}