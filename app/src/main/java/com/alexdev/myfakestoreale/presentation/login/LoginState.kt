package com.alexdev.myfakestoreale.presentation.login

data class LoginUiState(
    val username: String = "mor_2314",
    val password: String = "83r5^_",
    val checkedState: Boolean = false,
    val isLoading: Boolean = false,
    val usernameError: Boolean = false,
    val passwordError: Boolean = false,
    val loginError: String? = null,
    val isLoginSuccess: Boolean = false,
    val hasActiveSession: Boolean = false,
    val greeting: String = "Hola"
)

sealed class LoginUiEvent {
    data class OnUsernameChanged(val value: String) : LoginUiEvent()
    data class OnPasswordChanged(val value: String) : LoginUiEvent()
    data class OnRememberMeChanged(val value: Boolean) : LoginUiEvent()
    object OnLoginClicked : LoginUiEvent()
    object OnForgotPasswordClicked : LoginUiEvent()
    object OnRegisterClicked : LoginUiEvent()
    object OnErrorDismissed : LoginUiEvent()
    object OnContinueSessionClicked : LoginUiEvent()
    object OnSwitchAccountClicked : LoginUiEvent()
}

sealed class LoginSideEffect {
    data class ShowToast(val message: String) : LoginSideEffect()
    object NavigateToHome : LoginSideEffect()
    object NavigateToRegister : LoginSideEffect()
}