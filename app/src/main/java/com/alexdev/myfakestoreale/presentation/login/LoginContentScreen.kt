package com.alexdev.myfakestoreale.presentation.login

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.alexdev.myfakestoreale.R
import com.alexdev.myfakestoreale.presentation.login.components.LoginBackground
import com.alexdev.myfakestoreale.presentation.navigation.loginMenuOptions


@Composable
fun LoginContentScreen(
    viewModel: LoginViewModel = hiltViewModel(), //VM
    onLoginSuccess: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val errorUserTxt = stringResource(R.string.error_username_required)
    val errorPassTxt = stringResource(R.string.error_password_required)
    // Snackbar
    val snackbarHostState = remember { SnackbarHostState() }

    //efectos
    var isVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { isVisible = true }

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is LoginSideEffect.ShowToast -> {
                    snackbarHostState.showSnackbar(effect.message)
                }

                is LoginSideEffect.NavigateToHome -> {
                    onLoginSuccess()
                    viewModel.clearState()
                }

                is LoginSideEffect.NavigateToRegister -> {
                    Toast.makeText(context, "Navegando a Registro...", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    LaunchedEffect(key1 = state.loginError) {
        state.loginError?.let { error ->
            snackbarHostState.showSnackbar(error)
            viewModel.onEvent(LoginUiEvent.OnErrorDismissed)
        }
    }
    LoginBackground {
        Box(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
                .fillMaxHeight(1f)
                .background(Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            AnimatedVisibility(
                visible = isVisible,
                enter = slideInVertically(initialOffsetY = { it }) + fadeIn()
            ) {
                LoginForm(
                    state = state,
                    errorUserMsg = if (state.usernameError) errorUserTxt else null,
                    errorPassMsg = if (state.passwordError) errorPassTxt else null,
                    onEvent = viewModel::onEvent
                )
            }
        }
        Box(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
                .fillMaxHeight(1f)
                .background(Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.fillMaxHeight(0.18f))
                RoundedPersonImage()
            }
        }
        Box(
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            LoginBottomBar(
                items = loginMenuOptions,
                onOptionSelected = { route ->
                    Toast.makeText(context, "Navegar a: $route", Toast.LENGTH_SHORT).show()
                }
            )
        }
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 100.dp)
        ) { data ->
            Snackbar(
                snackbarData = data,
                containerColor = MaterialTheme.colorScheme.error,
                contentColor = MaterialTheme.colorScheme.onError
            )
        }
    }
}