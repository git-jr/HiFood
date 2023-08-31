package com.paradoxo.hifood.ui.activity.ui.theme

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

private val DarkColorScheme = darkColorScheme(
    primary = Purple200,
    secondary = Purple700,
    tertiary = Teal200
)

private val LightColorScheme = lightColorScheme(
    primary = colorPrimary,
    secondary = colorPrimaryVariant,
    tertiary = colorOnPrimary,

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun HiFoodTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}

@Composable
fun HiFoodTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    colorType: Color? = null,
    content: @Composable () -> Unit
) {


//    val view = LocalView.current
//    if (!view.isInEditMode) {
//        SideEffect {
//            val window = (view.context as Activity).window
//            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
//            WindowCompat.setDecorFitsSystemWindows(window, false)
//        }
//    }

        val colorScheme = if (darkTheme) {
            DarkColorScheme
        } else {
            LightColorScheme
        }

        val backgroundColor = colorType ?: colorScheme.background


    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = backgroundColor.copy(alpha = 0.5f).toArgb()
            window.navigationBarColor = backgroundColor.copy(alpha = 0.8f).toArgb()
        }
    }

        MaterialTheme(
            colorScheme = colorScheme.copy(
                background = backgroundColor,
                surface = backgroundColor,
            ),
            typography = Typography,
            content = content
        )
    }
