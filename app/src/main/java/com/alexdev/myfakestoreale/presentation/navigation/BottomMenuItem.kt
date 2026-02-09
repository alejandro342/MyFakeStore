package com.alexdev.myfakestoreale.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomMenuItem(
    val route: String,
    val title: String,
    val icon: ImageVector
)

val loginMenuOptions = listOf(
    BottomMenuItem("home", "Inicio", Icons.Default.Home),
    BottomMenuItem("products", "Productos", Icons.Default.ShoppingBag),
    BottomMenuItem("profile", "Perfil", Icons.Default.Person)
)