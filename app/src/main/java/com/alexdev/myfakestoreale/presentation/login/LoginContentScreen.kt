package com.alexdev.myfakestoreale.presentation.login

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.VpnKey
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.alexdev.myfakestoreale.presentation.login.components.LoginBackground
import com.alexdev.myfakestoreale.presentation.navigation.loginMenuOptions
import com.alexdev.myfakestoreale.ui.theme.Poppins


@Composable
fun LoginContentScreen(
    viewModel: LoginViewModel = hiltViewModel(), //VM
    onLoginSuccess: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    // Snackbar
    val snackbarHostState = remember { SnackbarHostState() }

    var checkedState by remember { mutableStateOf(false) }
    var username by remember { mutableStateOf("mor_2314") }
    var password by remember { mutableStateOf("83r5^_") }
    //efectos
    var isVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { isVisible = true }

    val context = LocalContext.current
    LaunchedEffect(key1 = state.token) {
        if (state.token != null) {
            onLoginSuccess()
            viewModel.clearState()
        }
    }

    LaunchedEffect(key1 = state.error) {
        if (state.error != null) {
            snackbarHostState.showSnackbar(
                message = state.error ?: "Error desconocido"
            )
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
                Card(
                    modifier = Modifier,
                    elevation = CardDefaults.cardElevation(10.dp),
                    shape = RoundedCornerShape(
                        topStart = 10.dp,
                        topEnd = 10.dp,
                        bottomStart = 10.dp,
                        bottomEnd = 10.dp
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState())
                            .padding(vertical = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Spacer(modifier = Modifier.height(30.dp))
                        MyTextField(
                            value = username,
                            onValueChange = { username = it },
                            label = "Usuario",
                            icon = Icons.Outlined.Person
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        MyTextField(
                            value = password,
                            onValueChange = { password = it },
                            label = "Contraseña",
                            icon = Icons.Outlined.VpnKey,
                            keyboardType = KeyboardType.Password
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End
                        ) {
                            MyText("Recordarme")
                            Spacer(modifier = Modifier.width(8.dp))
                            MySwitch(
                                checked = checkedState,
                                onCheckedChange = { isChecked ->
                                    checkedState = isChecked
                                }
                            )
                        }
                        MyButton(
                            text = "Iniciar Sesión",
                            onClick = { viewModel.login(username, password) },
                            isLoading = state.isLoading,
                            modifier = Modifier.padding(horizontal = 20.dp)
                        )
                    }
                }
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