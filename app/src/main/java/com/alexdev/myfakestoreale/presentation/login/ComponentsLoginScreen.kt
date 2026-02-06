package com.alexdev.myfakestoreale.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.alexdev.myfakestoreale.ui.theme.BluePastel
import com.alexdev.myfakestoreale.ui.theme.GrayVariant

@Composable
fun DefaultTextField(
    modifier: Modifier,
    value: String,
    onChangedValueText: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    colors: TextFieldColors,
    icon: ImageVector,
    hideText: Boolean = false,
    maxLine: Int = 1,
    singleLine: Boolean = true
) {
    TextField(
        value = value,
        onValueChange = { onChangedValueText(it) },
        modifier = modifier,
        label = { Text(text = label) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        colors = colors,
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = "Icon email",
                tint = GrayVariant
            )
        },
        visualTransformation = if (hideText) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        singleLine = singleLine,
        maxLines = maxLine
    )
}

@Composable
fun RoundedPersonImage() {
    Box(
        modifier = Modifier
            .size(80.dp)
            .background(BluePastel, shape = CircleShape)
    ) {
        Image(
            imageVector = Icons.Outlined.Person,
            contentDescription = null,
            modifier = Modifier
                .size(56.dp)
                .background(Color.White, CircleShape)
                .align(Alignment.Center)
        )
    }
}

// Definir el gradiente
val orangeGradient = Brush.horizontalGradient(
    colors = listOf(Color(0xFFFF9800), Color(0xFFFF5722)) // De Naranja a Rojo-Naranja
)