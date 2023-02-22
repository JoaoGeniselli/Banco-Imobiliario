package com.jgeniselli.banco.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightColorPalette = lightColors(
    primary = Primary,
    primaryVariant = PrimaryDark,
    onPrimary = OnPrimary,
    secondary = Secondary,
    onSecondary = OnSecondary,
)

@Composable
fun BancoImobiliarioTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = LightColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}