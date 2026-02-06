package com.alexdev.myfakestoreale.presentation.login

import androidx.compose.runtime.Composable

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit
) {
    LoginContentScreen(
        onLoginSuccess = onLoginSuccess
    )
}