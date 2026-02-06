package com.alexdev.myfakestoreale

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alexdev.myfakestoreale.presentation.login.LoginScreen
import com.alexdev.myfakestoreale.presentation.navigation.LoginRoute
import com.alexdev.myfakestoreale.presentation.navigation.ProductListRoute
import com.alexdev.myfakestoreale.presentation.products.ProductScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = LoginRoute
                    ) {

                        composable<LoginRoute> {
                            LoginScreen(
                                onLoginSuccess = {
                                    navController.navigate(ProductListRoute) {
                                        popUpTo<LoginRoute> { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable<ProductListRoute> {
                            ProductScreen()
                        }
                    }
                }
            }
        }
    }
}