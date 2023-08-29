package com.paradoxo.hifood.ui.activity.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = colorPrimary,
    primaryVariant = colorPrimaryVariant,
    onPrimary = colorOnPrimary,

    secondary = colorSecondary,
    onSecondary = colorOnSecondary,
    secondaryVariant = colorSecondaryVariant,

    background = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun HiFoodTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

@Composable
fun HiFoodTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    colorType: Color? = null,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    val tempColor = colorType ?: colors.background

    MaterialTheme(
        colors = colors.copy(
            background = tempColor,
            surface = tempColor,
        ),
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}