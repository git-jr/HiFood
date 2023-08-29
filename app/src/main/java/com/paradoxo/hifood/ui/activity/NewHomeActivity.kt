package com.paradoxo.hifood.ui.activity

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Animatable
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeGesturesPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.palette.graphics.Palette
import com.paradoxo.hifood.R
import com.paradoxo.hifood.ui.activity.ui.theme.HiFoodTheme

class NewHomeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val defaultBackgroundColor = MaterialTheme.colors.background
            val backgroundColor = remember { Animatable(defaultBackgroundColor) }

            HiFoodTheme(colorType = backgroundColor.value) {
                Box(
                    Modifier
                        .background(color = MaterialTheme.colors.background)
                        .fillMaxSize()
                        .safeGesturesPadding()
                ) {
                    val context = LocalContext.current
                    val drawbleList = listOf(
                        R.drawable.banner_hifood_vermelho,
                        R.drawable.banner_hifood_roxo,
                        R.drawable.banner_hifood_verde,
                        R.drawable.banner_hifood_amarelo,
                        R.drawable.banner_hifood_violeta
                    )
                    val bitmapsList = remember {
                        drawbleList.map { BitmapFactory.decodeResource(context.resources, it) }
                    }

                    val palletList =
                        remember { bitmapsList.map { Palette.from(it).generate() } }


                    val listOfColorsList: List<List<Pair<Color, String>>> = remember {
                        palletList.map { pallete ->
                            val colorList: List<Pair<Color, String>> =
                                palleteToColorList(pallete)
                            colorList
                        }
                    }

                    var visibleItem by remember { mutableIntStateOf(0) }

                    val lazyColumState = rememberLazyListState()

//                    this solution work but Android Studio show a warning to use derivedStateOf or collect by snapshotFlow (in use now)
//                    LaunchedEffect(lazyColumState.firstVisibleItemScrollOffset) {
//                        Log.d("TAG", "onCreate: ${lazyColumState.firstVisibleItemIndex}")
//                        visibleItem = lazyColumState.firstVisibleItemIndex
//                    }


                    LaunchedEffect(lazyColumState) {
                        snapshotFlow { lazyColumState.firstVisibleItemScrollOffset }
                            .collect {
                                Log.d("TAG", "onCreate: ${lazyColumState.firstVisibleItemIndex}")
                                visibleItem = lazyColumState.firstVisibleItemIndex
                            }
                    }

                    val currentColorList = listOfColorsList[visibleItem]

                    LaunchedEffect(currentColorList) {
                        backgroundColor.animateTo(currentColorList[5].first.copy(alpha = 0.5f))
                    }

                    Column {
                        LazyRow(
                            state = lazyColumState
                        ) {
                            items(drawbleList) { drawble ->
                                Box {
                                    Image(
                                        painter = painterResource(drawble),
                                        contentDescription = "HiFood Logo",
                                        modifier = Modifier
                                            .size(400.dp),
                                        contentScale = ContentScale.FillHeight
                                    )
                                }
                            }
                        }

                        Crossfade(
                            targetState = currentColorList,
                            label = "chage color list",
                            animationSpec = tween(500)
                        ) { currentColorListState ->
                            LazyColumn(
                                modifier = Modifier
                                    .defaultMinSize(minHeight = 200.dp)
                                    .fillMaxWidth()
                                    .background(color = Color.Red),
                                content = {
                                    items(currentColorListState) { color ->
                                        ColorItem(color = color.first, colorName = color.second)
                                    }
                                })
                        }
                    }
                }
            }
        }
    }
}

private fun palleteToColorList(palette: Palette): List<Pair<Color, String>> {

    val properties = listOf(
        "lightVibrantSwatch",
        "darkVibrantSwatch",
        "lightMutedSwatch",
        "darkMutedSwatch",
        "mutedSwatch",
        "vibrantSwatch"
    )


    val colorList = properties.map {
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
    return colorList
}


@Composable
fun ColorItem(color: Color, colorName: String) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(42.dp)
            .background(
                color = color
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = colorName,
            color = if (colorName == "darkMutedSwatch") Color.White else Color.Black
        )
    }
}
