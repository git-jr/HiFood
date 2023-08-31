package com.paradoxo.hifood.ui.dynamicColors

import androidx.compose.ui.graphics.Color
import androidx.palette.graphics.Palette
import com.paradoxo.hifood.R

val drawbleListBanner = listOf(
    R.drawable.banner_hifood_vermelho,
    R.drawable.banner_hifood_roxo,
    R.drawable.banner_hifood_amarelo,
    R.drawable.banner_hifood_lilas,
    R.drawable.banner_hifood_verde,
)


fun getPaletteColorList(palette: Palette) = run {
    val properties = listOf(
        "lightVibrantSwatch",
        "darkVibrantSwatch",
        "lightMutedSwatch",
        "darkMutedSwatch",
        "mutedSwatch",
        "vibrantSwatch"
    )

    properties.map {
        val swatch = when (it) {
            "lightVibrantSwatch" -> palette.lightVibrantSwatch
            "darkVibrantSwatch" -> palette.darkVibrantSwatch
            "lightMutedSwatch" -> palette.lightMutedSwatch
            "darkMutedSwatch" -> palette.darkMutedSwatch
            "mutedSwatch" -> palette.mutedSwatch
            "vibrantSwatch" -> palette.vibrantSwatch
            else -> null
        }

        Pair(swatch?.let { currentColor -> Color(currentColor.rgb) }
            ?: Color.Black, it)
    }.toMutableList()
}
