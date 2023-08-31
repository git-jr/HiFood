package com.paradoxo.hifood.ui.activity

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Animatable
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Badge
import androidx.compose.material3.BottomAppBarDefaults.windowInsets
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.palette.graphics.Palette
import com.paradoxo.hifood.R
import com.paradoxo.hifood.model.Store
import com.paradoxo.hifood.ui.activity.ui.theme.HiFoodTheme
import com.paradoxo.hifood.ui.activity.ui.theme.Montserrat
import com.paradoxo.hifood.ui.activity.ui.theme.MontserratAlternates
import com.paradoxo.hifood.ui.dynamicColors.drawbleListBanner

class NewHomeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val inTest = true
            if (inTest) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    MainScreen()
                }
            }
        }
    }

}

private fun getPaletteColorList(palette: Palette) = run {
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


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun MainScreen() {
    val defaultBackgroundColor = MaterialTheme.colorScheme.background
    val defaultOnBackgroundColor = MaterialTheme.colorScheme.onBackground
    val defaultErrorColor = MaterialTheme.colorScheme.error

    val backgroundColor = remember { Animatable(defaultBackgroundColor) }
    val onBackgroundColor = remember { Animatable(defaultOnBackgroundColor) }
    val errorColor = remember { Animatable(defaultErrorColor) }

    val context = LocalContext.current

    val bitmapsList = remember {
        drawbleListBanner.map { BitmapFactory.decodeResource(context.resources, it) }
    }

    val palletList = remember { bitmapsList.map { Palette.from(it).generate() } }

    val listOfColorsList: List<List<Pair<Color, String>>> = remember {
        palletList.map { pallete ->
            val colorList: List<Pair<Color, String>> =
                getPaletteColorList(pallete)
            colorList
        }
    }

    var visibleItemPager by remember { mutableIntStateOf(0) }
    val currentColorList = listOfColorsList[visibleItemPager]

    HiFoodTheme(
        backgroundColor = backgroundColor.value,
        onBackgroundColor = onBackgroundColor.value,
        errorColor = errorColor.value
    ) {
        Scaffold(
            bottomBar = {
                CustomNavigationBar()
            },
            topBar = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 56.dp)
                        .padding(end = 16.dp, start = 16.dp, top = 8.dp)
                        .safeDrawingPadding(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "R.Teste, 123",
                            fontSize = 16.sp,
                            fontFamily = MontserratAlternates,
                            fontWeight = FontWeight.Bold
                        )
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Expandir lista de endereções",
                            tint = Color.Red,
                        )
                    }

                    Box {
                        Box(
                            modifier = Modifier
                                .size(28.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Notifications,
                                contentDescription = "Expandir lista de endereções",
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(28.dp),
                            contentAlignment = Alignment.TopEnd,
                        ) {
                            Badge(
                                containerColor = MaterialTheme.colorScheme.error
                            ) {
                                Text(text = "1")
                            }
                        }
                    }
                }
            }
        ) { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Box {
                    LaunchedEffect(currentColorList) {
                        backgroundColor.animateTo(currentColorList[5].first.copy(alpha = 0.5f)) // 5 is the index of vibrantSwatch
                        onBackgroundColor.animateTo(currentColorList[1].first) // 1 is the index of darkVibrantSwatch
                        errorColor.animateTo(currentColorList[4].first) // 4 is the index of mutedSwatch
                    }
                    val pageCount = drawbleListBanner.size
                    val pagerState = rememberPagerState(pageCount = {
                        drawbleListBanner.size
                    })

                    LaunchedEffect(pagerState.currentPage) {
                        visibleItemPager = pagerState.currentPage
                    }

                    val verticalScrollState = rememberScrollState()

//                    LaunchedEffect(verticalScrollState.value) {
//                        // detect scroll to top
//                        if (verticalScrollState.value == 0) {
//                            backgroundColor.animateTo(defaultBackgroundColor)
//                        }
//                    }

                    Column(
                        modifier = Modifier
                            .verticalScroll(verticalScrollState)
                            .fillMaxWidth(),
                    ) {
                        GridContentTypes(Modifier.padding(8.dp))

                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            pageSize = PageSize.Fixed(350.dp),
                        ) { page ->
                            Box {
                                Image(
                                    painter = painterResource(drawbleListBanner[page]),
                                    contentDescription = "banner",
                                    modifier = Modifier
                                        .padding(horizontal = 8.dp)
                                        .height(150.dp),
                                )
                            }
                        }

                        Row(
                            Modifier
                                .height(20.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            repeat(pageCount) { iteration ->
                                val color =
                                    if (pagerState.currentPage == iteration) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onBackground.copy(
                                        alpha = 0.5f
                                    )
                                Box(
                                    modifier = Modifier
                                        .padding(2.dp)
                                        .clip(CircleShape)
                                        .background(color)
                                        .size(5.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        val stores = listOf(
                            Store(name = "Burger Rei", image = R.drawable.logo_stores_burger),
                            Store(name = "Mc Dugooes", image = R.drawable.logo_stores_mc),
                            Store(name = "StarButs", image = R.drawable.logo_stores_coffee),
                            Store(name = "Sanches", image = R.drawable.logo_stores_sanches),
                            Store(
                                name = "Fabrica do Cheesecake",
                                image = R.drawable.logo_stores_cake
                            ),
                        )


                        TextMoreContainer("Cupom e entrega gratis")
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                        ) { StoreList(stores.take(2)) }


                        TextMoreContainer("Ultimas lojas")
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp),
                        ) { StoreList(stores.takeLast(3)) }


                        Crossfade(
                            targetState = currentColorList,
                            label = "change color list",
                            animationSpec = tween(500)
                        ) { currentColorListState ->
                            LazyColumn(
                                modifier = Modifier
                                    .height(500.dp)
                                    .fillMaxWidth(),
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

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun CustomNavigationBar() {
    // Default NavigationBar not be used because ColorIndicator not be disable properly to simulate original aspect

    var currentDestination by remember { mutableStateOf(screenItems.first().route) }
    Row(
        Modifier
            .fillMaxWidth()
            .windowInsetsPadding(windowInsets)
            .height(80.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        val currentWidthScreen = LocalContext.current.resources.displayMetrics.widthPixels.dp.value

        screenItems.forEach { screen ->
            val isSelected = currentDestination == screen.route
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width((currentWidthScreen / (screenItems.size * 4)).dp)
                    .clickable {
                        currentDestination = screen.route
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                screen.resourceId?.let { assetIcon ->
                    val icon = painterResource(
                        id = if (isSelected) {
                            assetIcon.first
                        } else {
                            assetIcon.second
                        },
                    )

                    Box(
                        modifier = Modifier
                            .size(32.dp),
                        contentAlignment = Alignment.TopEnd,
                    ) {
                        Icon(
                            painter = icon,
                            contentDescription = null,
                            modifier = Modifier
                                .size(32.dp)
                                .alpha(
                                    if (isSelected) 1f else 0.5f
                                ),
                            tint = MaterialTheme.colorScheme.onBackground
                        )

                        if (screen.route == screenItems[2].route) {
                            Badge(
                                containerColor = MaterialTheme.colorScheme.error,
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(4.dp)
                                            .background(Color.White, CircleShape)
                                    )
                                }
                            }
                        }
                    }

                    Text(
                        text = screen.route,
                        fontSize = 10.sp,
                        fontFamily = MontserratAlternates,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                    )

                }
            }

        }
    }
}

@Composable
fun GridContentTypes(modifier: Modifier = Modifier) {
    val types = listOf(
        Pair("Restaurantes", R.drawable.content_type_restaurante),
        Pair("Mercado", R.drawable.content_type_mercado),
        Pair("Bebidas", R.drawable.content_type_bebidas),
        Pair("Shopping", R.drawable.content_type_shopping),
    )

    LazyVerticalGrid(
        modifier = modifier
            .fillMaxWidth()
            .height(130.dp),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(types) { item ->
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.background.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(12.dp)
                    .fillMaxHeight(),
            ) {
                Text(
                    text = item.first,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 12.sp,
                    fontFamily = MontserratAlternates,
                    fontWeight = FontWeight.Bold,
                )

                Image(
                    painterResource(id = item.second),
                    contentDescription = "Imagem ${item.first}",
                    modifier = Modifier
                        .size(36.dp),
                )
            }
        }
    }
}

@Composable
private fun StoreList(stores: List<Store>) {
    LazyRow(
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        items(stores) { store ->
            Column(
                modifier = Modifier
                    .size(width = 80.dp, height = 120.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Image(
                    painter = painterResource(store.image),
                    contentDescription = store.name,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Text(
                    text = store.name,
                    modifier = Modifier
                        .padding(8.dp),
                    fontSize = 12.sp,
                    maxLines = 2,
                    fontFamily = MontserratAlternates,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Composable
private fun TextMoreContainer(text: String, onClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 20.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Bold,
        )

        Text(
            text = "Ver mais",
            fontSize = 16.sp,
            fontFamily = MontserratAlternates,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .clickable {
                    onClick()
                }
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}


sealed class Destinations(val route: String, val resourceId: Pair<Int, Int>? = null) {
    object Home : Destinations("Início", Pair(R.drawable.ic_home_fill, R.drawable.ic_home_outlined))

    object Search :
        Destinations("Busca", Pair(R.drawable.ic_search_fill, R.drawable.ic_search_outlined))

    object Request :
        Destinations("Pedidos", Pair(R.drawable.ic_request_fill, R.drawable.ic_request_outlined))

    object Profile :
        Destinations("Perfil", Pair(R.drawable.ic_profile_fill, R.drawable.ic_profile_outlined))
}

val screenItems = listOf(
    Destinations.Home,
    Destinations.Search,
    Destinations.Request,
    Destinations.Profile
)
