package com.alexdev.myfakestoreale.presentation.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.VpnKey
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.alexdev.myfakestoreale.R
import com.alexdev.myfakestoreale.presentation.navigation.BottomMenuItem
import com.alexdev.myfakestoreale.ui.theme.Poppins

@Composable
fun RoundedPersonImage() {
    Box(
        modifier = Modifier
            .size(80.dp)
            .background(MaterialTheme.colorScheme.primaryContainer, shape = CircleShape)
    ) {
        Icon(
            imageVector = Icons.Outlined.Person,
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.Center),
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Composable
fun MyTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = keyboardType == KeyboardType.Password,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default,
    errorMsg: String? = null
) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth()) {
        TextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 5.dp),
            value = value,
            onValueChange = onValueChange,
            label = {
                Text(
                    text = label,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            shape = RoundedCornerShape(16.dp),

            colors = TextFieldDefaults.colors(
                focusedContainerColor = if (errorMsg != null) MaterialTheme.colorScheme.errorContainer.copy(
                    alpha = 0.1f
                ) else MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = if (errorMsg != null) MaterialTheme.colorScheme.errorContainer.copy(
                    alpha = 0.1f
                ) else MaterialTheme.colorScheme.background,
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                cursorColor = if (errorMsg != null) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                focusedLabelColor = if (errorMsg != null) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                errorLabelColor = MaterialTheme.colorScheme.error
            ),
            isError = errorMsg != null,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            keyboardActions = onAction,
            leadingIcon = if (icon != null) {
                {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = if (errorMsg != null) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                    )
                }
            } else null,

            trailingIcon = if (isPassword) {
                {
                    val image = if (isPasswordVisible) {
                        Icons.Filled.VisibilityOff
                    } else {
                        Icons.Filled.Visibility
                    }

                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(
                            imageVector = image,
                            contentDescription = "Toggle password visibility",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            } else null,

            visualTransformation = if (isPassword && !isPasswordVisible) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            }
        )
        if (errorMsg != null) {
            Text(
                text = errorMsg,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 36.dp, top = 2.dp) // Alineado bonito
            )
        }
    }
}

@Composable
fun MyText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.background,
        fontFamily = Poppins
    )
}

@Composable
fun MySwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Switch(
        modifier = modifier,
        checked = checked,
        onCheckedChange = onCheckedChange,
        colors = SwitchDefaults.colors(
            checkedThumbColor = MaterialTheme.colorScheme.secondary,
            checkedTrackColor = MaterialTheme.colorScheme.secondaryContainer,
            uncheckedThumbColor = MaterialTheme.colorScheme.outline,
            uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant,
            uncheckedBorderColor = Color.Transparent
        )
    )
}

@Composable
fun LoginBottomBar(
    items: List<BottomMenuItem>,
    defaultSelectedRoute: String = "home",
    onOptionSelected: (String) -> Unit
) {
    var selectedItem by remember { mutableStateOf(defaultSelectedRoute) }

    Card(
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 30.dp)
            .fillMaxWidth()
            .height(65.dp),
        shape = CircleShape,
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                val isSelected = selectedItem == item.route
                val iconColor =
                    if (isSelected) MaterialTheme.colorScheme.secondary else Color.White.copy(alpha = 0.6f)

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable {
                            selectedItem = item.route
                            onOptionSelected(item.route)
                        }
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        tint = iconColor,
                        modifier = Modifier.size(26.dp)
                    )
                    AnimatedVisibility(visible = isSelected) {
                        Box(
                            modifier = Modifier
                                .padding(top = 4.dp)
                                .size(4.dp)
                                .background(MaterialTheme.colorScheme.secondary, CircleShape)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MyButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(50.dp)
            .fillMaxWidth(),
        enabled = enabled && !isLoading,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
        )
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(24.dp),
                strokeWidth = 3.dp
            )
        } else {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun LoginForm(
    state: LoginUiState,
    errorUserMsg: String?,
    errorPassMsg: String?,
    onEvent: (LoginUiEvent) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Card(
        elevation = CardDefaults.cardElevation(10.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            // USUARIO
            MyTextField(
                value = state.username,
                onValueChange = { onEvent(LoginUiEvent.OnUsernameChanged(it)) }, // Evento
                label = stringResource(R.string.login_username_label),
                errorMsg = errorUserMsg, // Estado
                imeAction = ImeAction.Next,
                onAction = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                icon = Icons.Outlined.Person
            )

            Spacer(modifier = Modifier.height(20.dp))

            // CONTRASEÑA
            MyTextField(
                value = state.password,
                onValueChange = { onEvent(LoginUiEvent.OnPasswordChanged(it)) }, // Evento
                label = stringResource(R.string.login_password_label),
                errorMsg = errorPassMsg, // Estado
                icon = Icons.Outlined.VpnKey,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
                onAction = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                    onEvent(LoginUiEvent.OnLoginClicked)
                })
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.login_forgot_password),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable {
                        onEvent(LoginUiEvent.OnForgotPasswordClicked) // Dispara evento
                    }
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    MyText(stringResource(R.string.login_remember_me))
                    Spacer(modifier = Modifier.width(8.dp))
                    MySwitch(
                        checked = state.checkedState,
                        onCheckedChange = { isChecked ->
                            onEvent(LoginUiEvent.OnRememberMeChanged(isChecked)) // Enviamos evento
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))

            MyButton(
                text = stringResource(R.string.login_button),
                onClick = { onEvent(LoginUiEvent.OnLoginClicked) },
                isLoading = state.isLoading,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
        }
    }
}

@Composable
fun ResumeSessionView(
    username: String,
    onContinueClick: () -> Unit,
    onSwitchAccountClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Hola, ${username.ifEmpty { "Usuario" }}",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = onContinueClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(10.dp)
        ) {
            MyText("Entrar")
        }

        Spacer(modifier = Modifier.height(20.dp))

        TextButton(onClick = onSwitchAccountClick) {
            Text("Usar otra cuenta", color = MaterialTheme.colorScheme.secondary)
        }
    }
}