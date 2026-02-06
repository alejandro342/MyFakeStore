package com.alexdev.myfakestoreale.presentation.login

data class LoginState(
    val isLoading: Boolean = false,
    val token: String? = null,
    val error: String? = null
)