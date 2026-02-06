package com.alexdev.myfakestoreale.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = MidnightBlue,
    onPrimary = PureWhite,
    primaryContainer = MidnightBlueLight,
    onPrimaryContainer = PureWhite,

    secondary = SunsetCoral,
    onSecondary = PureWhite,
    secondaryContainer = SoftCoral,
    onSecondaryContainer = MidnightBlue,

    background = CulturedWhite,
    onBackground = Gunmetal,

    surface = PureWhite,
    onSurface = Gunmetal,

    outline = LightGrayBorder,
    error = FashionRed
)


private val DarkColorScheme = darkColorScheme(
    primary = PureWhite,
    onPrimary = MidnightBlue,
    secondary = SunsetCoral,
    background = MidnightBlue,
    surface = Color(0xFF2C3E50),
    onBackground = PureWhite,
    onSurface = PureWhite
)

@Composable
fun FakeStoreTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}