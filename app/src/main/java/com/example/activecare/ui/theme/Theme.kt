package com.example.activecare.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Immutable
data class AppColors(
    val materialColors: ColorScheme,
) {
    val background = materialColors.background
    val DarkBack = materialColors.primary
    val LightBack = materialColors.secondary
    val DarkText = materialColors.onPrimary
    val LightText = materialColors.onSecondary
    val GrayTextFiled = materialColors.onPrimaryContainer
}

val appColors = AppColors(
    materialColors = darkColorScheme(
        background = Color.White,
        primary = DarkBack,
        secondary = LightBack,
        onPrimary = DarkText,
        onSecondary = LightText,
        onPrimaryContainer = GrayTextFiled,
    )
)


val localAppColors = staticCompositionLocalOf { appColors }

@Composable
fun ActiveCareTheme(
    content: @Composable () -> Unit,
) {
    val localAppColors = staticCompositionLocalOf { appColors }
    CompositionLocalProvider(localAppColors provides appColors) {
        MaterialTheme(
            colorScheme = appColors.materialColors,
            shapes = appShapes,
            typography = appTypography,
            content = content
        )
    }
}

object AppTheme {
    val colors: AppColors
        @Composable
        get() = localAppColors.current

    val shapes: Shapes
        @Composable
        get() = MaterialTheme.shapes

    val typography: Typography
        @Composable
        get() = MaterialTheme.typography
}
